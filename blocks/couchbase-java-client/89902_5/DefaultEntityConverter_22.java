                if (encryptionProvider != null) {
                    if (value == null) {
                        content.putNull(name, encryptionProvider);
                    } else if (value instanceof String) {
                        content.put(name, new String((String)value), encryptionProvider);
                    } else if (value instanceof Boolean) {
                        content.put(name, new Boolean((Boolean)value).booleanValue(), encryptionProvider);
                    } else if (value instanceof Integer) {
                        content.put(name, new Integer((Integer)value).intValue(), encryptionProvider);
                    } else if (value instanceof Long) {
                        content.put(name, new Long((Long)value).longValue(), encryptionProvider);
                    } else {
                        content.put(name, new Double((Double)value).doubleValue(), encryptionProvider);
                    }
                } else {
                    content.put(name, value);
                }
