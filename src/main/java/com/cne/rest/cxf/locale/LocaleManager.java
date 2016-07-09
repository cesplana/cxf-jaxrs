package com.cne.rest.cxf.locale;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cne.rest.cxf.util.LocaleProvider;

/**
 * 
 * @author lenovo
 *
 * @param <T_model>
 */
public class LocaleManager<T_model> {

    private Logger LOGGER = LoggerFactory.getLogger(LocaleManager.class);

    private String language;

    private List<LocaleMapping> mapping;

    /**
     * 
     * @param model
     */
    public void applyAll(T_model model) {
        System.out.println("******* LOCALE MAPPING *********");
        
        for (LocaleMapping mappingAttr : mapping) {
            System.out.println(mappingAttr.getAttribute() + " " + mappingAttr.getValue());

            try {
                PropertyUtils.setNestedProperty(model, mappingAttr.getAttribute(), LocaleProvider.applyLocale(mappingAttr.getValue(), this.language));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<LocaleMapping> getMapping() {
        return mapping;
    }

    public void setMapping(List<LocaleMapping> mapping) {
        this.mapping = mapping;
    }

    public String getLanguage() {
        return language;
    }

}
