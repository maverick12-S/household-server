package com.example.household_server.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.household_server.domain.model.Transaction;
import com.example.household_server.infrastructure.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }

    public List<Transaction> findAll(){
        return repository.findAll();
    }

    public Optional<Transaction> findById(Long id){
        return repository.findById(id);
    }

    public Transaction save(Transaction transaction){
        return repository.save(transaction);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
