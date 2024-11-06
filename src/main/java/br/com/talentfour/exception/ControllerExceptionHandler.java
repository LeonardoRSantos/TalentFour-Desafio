package br.com.talentfour.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.RollbackException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
            StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        log.error("PRECONDITION_FAILED: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        log.error("PRECONDITION_FAILED: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ServiceBusinessException.class)
    public ResponseEntity<StandardError> serviceBusinessException(ServiceBusinessException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.PRECONDITION_FAILED.value(), "Houve um problema ao solicitar a operação", e.getMessage(), request.getRequestURI());
        log.error("PRECONDITION_FAILED: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), "Acesso não autorizado", e.getMessage(), request.getRequestURI());
        log.error("UNAUTHORIZED: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), "Acesso não autorizado - Privilégios Insuficientes", e.getMessage(), request.getRequestURI());
        log.error("UNAUTHORIZED: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<StandardError> contraintViolationException(TransactionSystemException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de integridade de dados",
                "Não foi possível realizar a transação: "+e.getMessage(),
                request.getContextPath()
        );
        log.error("BAD REQUEST: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<StandardError> handleNotValidException(RollbackException ex, HttpServletRequest request){
        StandardError err = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Violação de integridade de dados",
                "Não foi possível realizar a transação",
                request.getContextPath()
        );
        log.error("BAD REQUEST: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityViolationException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de integridade de dados",
                "Não foi possível realizar a transação: "+e.getMessage(),
                request.getContextPath()
        );
        log.error("BAD REQUEST: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de validação", "", request.getRequestURI());

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            fieldErrors.forEach(fieldError -> err.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        log.error("BAD REQUEST: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> validation(Exception e, HttpServletRequest request) {
        ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de validação", "", request.getRequestURI());

        log.error("BAD REQUEST: {} => {}", err.getError(), err.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
