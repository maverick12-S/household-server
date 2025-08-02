package com.example.household_server.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.household_server.application.service.TransactionService;
import com.example.household_server.common.LogUtil;
import com.example.household_server.domain.model.Transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private  TransactionService transactionService;
    @Autowired
    private LogUtil logging;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(HttpServletRequest request,HttpServletResponse response) {
        List<Transaction> transaction = transactionService.findAllByEmail();
        logging.logAccess(request, response);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) {
        Transaction transaction = transactionService.findById(id);
        logging.logAccess(request, response);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
                
    }

    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody Transaction requestTransaction ,HttpServletRequest request,HttpServletResponse response) {
        Transaction transaction = transactionService.create(requestTransaction);
        logging.logAccess(request, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id,
            @RequestBody Transaction requestTransaction
            ,HttpServletRequest request
            ,HttpServletResponse response) {
        Transaction transaction = transactionService.update(id, requestTransaction);
        logging.logAccess(request, response);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestBody List<Long> ids,HttpServletRequest request,HttpServletResponse response) {
        transactionService.deleteByIds(ids);
        logging.logAccess(request, response);
        return ResponseEntity.noContent().build();
    }
}