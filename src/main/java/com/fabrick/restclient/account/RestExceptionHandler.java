package com.fabrick.restclient.account;


import com.fabrick.restclient.account.ApiError;
import com.fabrick.restclient.account.ErrorAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = ErrorAccountException.class)
    public ResponseEntity<ApiError> handleErrorAccountException(Exception e) {
        ApiError apiError = new ApiError("API1000", "Errore Tecnico");
        apiError.setCode("API1000");
        apiError.setDescription("Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ErrorTransactionException.class)
    public ResponseEntity<ApiError> handTransactionException(Exception e) {
        ApiError apiError = new ApiError("SC000", "Errore Tecnico");
        apiError.setCode("SC000");
        apiError.setDescription("SC000-Errore tecnico server");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ErrorBalanceException.class)
    public ResponseEntity<ApiError> handBalanceException(Exception e) {
        ApiError apiError = new ApiError("0000", "Errore Tecnico");
        apiError.setCode("SC000");
        apiError.setDescription("SC000-Errore tecnico server");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
