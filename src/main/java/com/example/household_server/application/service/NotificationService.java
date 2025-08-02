package com.example.household_server.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.household_server.application.exception.ReservationException;
import com.example.household_server.domain.model.NotificationReservation;
import com.example.household_server.infrastructure.repository.NotificationReservationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationReservationRepository repository;

    public void reserveOrUpdate(String email){
        try{
            repository
                .findByEmail(email)
                .ifPresentOrElse( this::updateReservation,() -> createReservation(email));
        }catch(Exception e){
            throw new ReservationException("日時報告予約処理でエラーが発生しました。管理者に連絡してください。");
        }

    }

    private void updateReservation(NotificationReservation reservation){
        if(reservation.getReserved()){
           return; 
        }
        reservation.setReserved(true);
        repository.save(reservation);
    }
    private void createReservation(String email){
        NotificationReservation newReservation = new NotificationReservation();
        newReservation.setEmail(email);
        newReservation.setReserved(true);
        repository.save(newReservation);
    }
}
