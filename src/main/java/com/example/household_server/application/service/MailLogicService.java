package com.example.household_server.application.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.household_server.domain.model.NotificationReservation;
import com.example.household_server.domain.model.Transaction;
import com.example.household_server.infrastructure.repository.NotificationReservationRepository;
import com.example.household_server.infrastructure.repository.TransactionRepository;

@Service
public class MailLogicService {

    private static final Logger logger = LoggerFactory.getLogger(MailLogicService.class);

    @Autowired
    private NotificationReservationRepository notificationReservationRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MailSenderService mailSenderService;

    /**
     * 家計簿サマリメールを、通知予約が有効なユーザーに送信する。
     */
    public void sendDailySummaryEmails() {
        List<NotificationReservation> recipients = fetchNotificationRecipients();
        for (NotificationReservation reservation : recipients) {
            try {
                String email = reservation.getEmail();
                SummaryData summary = calculateSummary();
                String body = formatSummaryMessage(summary);
                mailSenderService.sendMail(email, "家計簿サマリ", body);
                logger.info("家計簿サマリ送信成功: {}", email);
            } catch (Exception e) {
                logger.error("家計簿サマリ送信失敗: {}", reservation.getEmail(), e);
            }
        }
    }

    /**
     * 通知予約が有効なユーザー一覧を取得する。
     */
    private List<NotificationReservation> fetchNotificationRecipients() {
        return notificationReservationRepository.findByReservedTrue();
    }

    /**
     * 対象ユーザーの前日の収入・支出を集計し、1日あたりの利用可能金額を計算する。
     */
    private SummaryData calculateSummary() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String dateStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Transaction> transactions = transactionRepository.findByDate(dateStr);

        double income = transactions.stream()
                .filter(tx -> "income".equals(tx.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(tx -> "expense".equals(tx.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double usable = income - expense;
        int remainingDays = calculateRemainingDays();
        double dailyBudget = remainingDays > 0 ? usable / remainingDays : 0;

        return new SummaryData(income, expense, remainingDays, dailyBudget);
    }

    /**
     * メール本文を整形する。
     */
    private String formatSummaryMessage(SummaryData summary) {
        String template = "昨日の収入: {0} 円\n支出: {1} 円\n残りの日数: {2} 日\n1日当たり使える金額: {3} 円。";
        return MessageFormat.format(template,
                summary.income(),
                summary.expense(),
                summary.remainingDays(),
                summary.dailyBudget());
    }

    /**
     * 当日から月末までの日数を計算する。
     */
    private int calculateRemainingDays() {
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return (int) ChronoUnit.DAYS.between(today, endOfMonth) + 1;
    }

    /**
     * サマリ計算結果を保持するレコードクラス
     */
    private record SummaryData(
            double income,
            double expense,
            int remainingDays,
            double dailyBudget
    ) {}
}
