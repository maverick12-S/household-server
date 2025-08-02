package com.example.household_server.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.household_server.application.dto.NotificationRequest;
import com.example.household_server.application.service.NotificationService;
import com.example.household_server.common.LogUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private LogUtil logging;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody NotificationRequest requests,HttpServletRequest request,HttpServletResponse response) {
        notificationService.reserveOrUpdate(requests.getEmail());
        logging.logAccess(request, response);
        return ResponseEntity.noContent().build();
        
    }
    
}
