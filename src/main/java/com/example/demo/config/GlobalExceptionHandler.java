package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Global exception handler to handle common errors across the application
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handle errors when a String cannot be converted to an Integer (like when "null" is passed as an ID)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, RedirectAttributes redirectAttributes) {
        logger.error("Type mismatch error: " + ex.getMessage());
        
        String parameterName = ex.getName();
        String errorMessage = String.format(
            "Failed to convert value '%s' for parameter '%s' to required type", 
            ex.getValue(), 
            parameterName
        );
        
        // For employee editing with invalid ID
        if (parameterName.equals("empId")) {
            redirectAttributes.addFlashAttribute("error", "Invalid employee ID. Please select an employee from the list.");
            return "redirect:/mvc/employees";
        }
        
        // Default case
        redirectAttributes.addFlashAttribute("error", errorMessage);
        return "redirect:/mvc/employees";
    }
    
    /**
     * Handle generic exceptions
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        logger.error("Unexpected error: " + ex.getMessage(), ex);
        
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        model.addAttribute("stackTrace", ex.getStackTrace());
        
        return "error";
    }
}
