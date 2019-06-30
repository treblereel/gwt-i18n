package org.gwtproject.i18n.rg.resource;

import org.gwtproject.i18n.ext.ConfigurationProperty;
import org.gwtproject.i18n.ext.SelectionProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 12/5/18
 */
public class StandardSelectionProperty implements SelectionProperty {

    private final String name, defaultValue;
    private SortedSet<String> values = new TreeSet<>();

    private final String activeValue;


    public StandardSelectionProperty(String name, String activeValue, String defaultValue){
        this.activeValue = activeValue;
        this.name = name;
        this.defaultValue = defaultValue;
        for (String s : activeValue.split(",")) {
            values.add(s);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCurrentValue() {
        return activeValue;
    }

    @Override
    public SortedSet<String> getPossibleValues() {
        return values;
    }

    @Override
    public String getFallbackValue() {
        return defaultValue;
    }
}
