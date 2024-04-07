package com.microservice.usuarios.utils;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class includes methods for date and string modification.
 *
 * @author Daniel Vega
 * @version 1.0, 06/04/2024
 */
public class Utils {

    public static LocalDateTime getCurrentDateTime() {
        LocalDateTime formatDateTime = LocalDateTime.now();
        return formatDateTime;
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}