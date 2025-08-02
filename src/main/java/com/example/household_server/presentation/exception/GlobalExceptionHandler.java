package com.example.household_server.presentation.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.household_server.application.exception.BadRequestException;
import com.example.household_server.application.exception.NotFoundException;
import com.example.household_server.application.exception.ReservationException;
import com.example.household_server.common.LogUtil;
import com.example.household_server.presentation.exception.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Autowired
    private LogUtil logging;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex,HttpServletRequest request,HttpServletResponse response){
        logging.logError(request, ex);
        logging.logAccess(request, response);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            ex.getMessage(),
            request.getRequestURI());
            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadException(Exception ex,HttpServletRequest request,HttpServletResponse response){
       logging.logError(request, ex);
       logging.logAccess(request, response);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage(),
            request.getRequestURI());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception ex,HttpServletRequest request,HttpServletResponse response){
        logging.logError(request, ex);
        logging.logAccess(request, response);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI());
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationException(Exception ex,HttpServletRequest request,HttpServletResponse response){
        logging.logError(request, ex);
        logging.logAccess(request, response);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            HttpStatus.NO_CONTENT.value(),
            "Reservation Conflict",
            ex.getMessage(),
            request.getRequestURI());
            return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

}
