package br.com.customers.service.util;


import br.com.customers.web.rest.errors.BadRequestAlertException;

/**
 * Utility class to throw errors
 */
public class ThrowUtils {

    public static void badRequest(String defaultMessage, String entityName, String errorKey) {
        throw new BadRequestAlertException(defaultMessage, entityName, errorKey);
    }

}
