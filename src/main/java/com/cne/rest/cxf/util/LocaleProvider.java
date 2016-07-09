package com.cne.rest.cxf.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * 
 * @author lenovo
 *
 */
public class LocaleProvider {

    private static MessageSource messageSource;

    /**
     * 
     * @param key
     * @param language
     * @return
     */
    public static String applyLocale(String key, String language) {
        
        if (messageSource != null) {
            return messageSource.getMessage(key, null, new Locale(language));
        }
        
        return null;
        
    }

    public static void setMessageSource(MessageSource messageSource) {
        LocaleProvider.messageSource = messageSource;
    }
    
}
