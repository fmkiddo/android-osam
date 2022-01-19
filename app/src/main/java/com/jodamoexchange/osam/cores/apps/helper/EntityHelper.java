package com.jodamoexchange.osam.cores.apps.helper;

import com.jodamoexchange.osam.cores.Attribute;
import com.jodamoexchange.osam.cores.Entity;
import com.jodamoexchange.osam.cores.MutableAttribute;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityHelper {

    private static class EntityClass implements Entity {

        private Map<String, Attribute<?>> attributes;

        public EntityClass () {
            this.attributes = new LinkedHashMap<>();
        }

        public EntityClass (Map<String, ?> predefined) {
            this.attributes = new LinkedHashMap<>();
            this.loadPredefined(predefined);
        }

        @Override
        public boolean hasAttribute(String name) {
            return this.attributes.containsKey(name);
        }

        @Override
        public <V> boolean putAttribute(String name, V value) {
            if (!this.hasAttribute(name)) this.attributes.put (name, this.attribute(value));
            else {
                MutableAttribute<V> attribute = (MutableAttribute<V>) this.attributes.get (name);
                if (attribute.isSameClassType(value)) attribute.putValue(value);
                else this.attributes.put(name, this.attribute(value));
            }
            return this.hasAttribute(name);
        }

        @Override
        public <V> V getAttribute(String name) {
            if (!this.hasAttribute(name)) return null;
            else {
                Attribute<V> attribute = (Attribute<V>) this.attributes.get(name);
                return attribute.getValue();
            }
        }

        private <V> Attribute<V> attribute (V value) {
            return AttributeHelper.newAttribute(value);
        }

        private boolean loadPredefined (Map<String, ?> predefined) {
            for (Map.Entry<String, ?> entry : predefined.entrySet()) this.putAttribute(entry.getKey(), entry.getValue());
            return this.attributes.size() == predefined.size();
        }
    }

    private EntityHelper () { }

    public static Entity newEntity () {
        return new EntityClass();
    }

    public static Entity newEntity (Map<String, ?> predefined) {
        return new EntityClass(predefined);
    }
}
