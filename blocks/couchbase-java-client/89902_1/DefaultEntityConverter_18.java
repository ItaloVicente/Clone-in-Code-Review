                if (encryptionProvider != null) {
                    if (value == null) {
                        content.putNull(name, encryptionProvider);
                    } else if (value instanceof String) {
                        content.put(name, (String)value, encryptionProvider);
                    } else if (value instanceof Boolean) {
                        content.put(name, value, encryptionProvider);
                    } else if (value instanceof Integer) {
                        content.put(name, (Integer) value, encryptionProvider);
                    } else if (value instanceof Long) {
                        content.put(name, (Long)value, encryptionProvider);
                    } else {
                        content.put(name, (Double)value, encryptionProvider);
                    }
                } else {
                    content.put(name, value);
                }
