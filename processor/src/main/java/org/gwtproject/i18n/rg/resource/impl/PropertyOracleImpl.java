package org.gwtproject.i18n.rg.resource.impl;

import org.gwtproject.i18n.context.AptContext;
import org.gwtproject.i18n.ext.*;
import org.gwtproject.i18n.rg.resource.ConfigurationProperties;
import org.gwtproject.i18n.rg.resource.StandardSelectionProperty;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 12/5/18
 */
public class PropertyOracleImpl implements PropertyOracle {
    public final ConfigurationProperties configurationProperties;

    public PropertyOracleImpl(AptContext aptContext) {
        configurationProperties = new ConfigurationProperties(aptContext.filer);
    }

    @Override
    public ConfigurationProperty getConfigurationProperty(TreeLogger logger, String propertyName) throws UnableToCompleteException {
        ConfigurationProperty configurationProperty;
        try {
            configurationProperty = configurationProperties.getConfigurationProperty(propertyName);
        } catch (BadPropertyValueException e) {
            logger.log(TreeLogger.Type.ERROR, "Unable to get configuration property : " + propertyName);
            throw new UnableToCompleteException();
        }
        return configurationProperty;
    }

    @Override
    public SelectionProperty getSelectionProperty(TreeLogger logger, String propertyName) throws UnableToCompleteException {
        String value = System.getProperty(propertyName);
        if (value != null) {
            try {
                return new StandardSelectionProperty(propertyName, value, configurationProperties.getConfigurationProperty(propertyName).asSingleValue());
            } catch (BadPropertyValueException e) {
                throw new UnableToCompleteException(e);
            }
        }
        logger.log(TreeLogger.Type.ERROR, "Unable to get selection property : " + propertyName);
        throw new UnableToCompleteException();
    }
}
