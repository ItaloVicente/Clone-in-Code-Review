        /* Add the key/value pair */
        if (keys == null) {
            keys = new String[] { key };
            values = new Object[] { value };
            return;
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        String[] newKeys = new String[keys.length + 1];
        Object[] newValues = new Object[values.length + 1];
        System.arraycopy(keys, 0, newKeys, 0, keys.length);
        System.arraycopy(values, 0, newValues, 0, values.length);
        newKeys[keys.length] = key;
        newValues[values.length] = value;
        keys = newKeys;
        values = newValues;
    }
