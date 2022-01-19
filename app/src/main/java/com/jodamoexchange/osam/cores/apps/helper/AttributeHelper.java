package com.jodamoexchange.osam.cores.apps.helper;

import com.jodamoexchange.osam.cores.Attribute;
import com.jodamoexchange.osam.cores.MutableAttribute;

public class AttributeHelper {

    static private class AttributeClass<V> implements MutableAttribute<V> {

        private V value;

        public AttributeClass(V value) {
            this.value = value;
        }

        @Override
        public boolean isSameClassType(V value) {
            return this.value.getClass().equals(value.getClass());
        }

        @Override
        public int compareTo(V newValue) {
            if (this.value.equals(newValue)) return 0;
            return 1;
        }

        @Override
        public boolean putValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return this.compareTo(oldValue) == 0;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }

    private AttributeHelper () { }

    public static <V> Attribute<V> newAttribute (V value) {
        return new AttributeClass (value);
    }
}
