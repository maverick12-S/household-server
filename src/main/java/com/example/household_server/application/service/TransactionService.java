package com.example.household_server.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.household_server.application.exception.NotFoundException;
import com.example.household_server.common.AuthUtil;
import com.example.household_server.common.LogUtil;
import com.example.household_server.domain.model.Transaction;
import com.example.household_server.infrastructure.repository.TransactionRepository;

@Service
public class TransactionService {
    
    private final TransactionRepository repository;
    private  LogUtil logging;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }

    public List<Transaction> findAllByEmail(){
        String email = AuthUtil.getEmail();
        return repository.findByEmail(email);
    }

    public Transaction findById(Long id){
        return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("データが見つかりませんでした。管理者に連絡してください。"));
    }

    public Transaction create(Transaction transaction){
        String email = AuthUtil.getEmail();
        transaction.setEmail(email);
        return repository.save(transaction);
    }

    public Transaction update(Long id,Transaction transaction){
        if (!repository.findById(id).isPresent()) {
            transaction.setId(id);
            return repository.save(transaction);
        } else {
            throw new NotFoundException("更新対象のデータが見つかりません。管理者に連絡してください。");
        }
    }

    public void deleteByIds(List<Long> ids){
        for (Long id : ids) {
                if (repository.findById(id).isPresent()) {
                     repository.deleteById(id);
                     return;
                }
        throw new NotFoundException("削除対象のデータが見つかりません。管理者に連絡してください。");
            }
    }
}
