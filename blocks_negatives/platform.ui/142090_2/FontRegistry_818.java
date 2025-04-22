    }

    /**
     * Reads the resource bundle.  This puts FontData[] objects
     * in the mapping table.  These will lazily be turned into
     * real Font objects when requested.
     */
    private void readResourceBundle(ResourceBundle bundle, String bundleName)
            throws MissingResourceException {
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int pos = key.lastIndexOf('.');
            if (pos == -1) {
                stringToFontData.put(key, new FontData[] { makeFontData(bundle
                        .getString(key)) });
            } else {
                String name = key.substring(0, pos);
                int i = 0;
                try {
                    i = Integer.parseInt(key.substring(pos + 1));
                } catch (NumberFormatException e) {
                    throw new MissingResourceException(
                            "Wrong key format ", bundleName, key); //$NON-NLS-1$
                }
                FontData[] elements = stringToFontData.get(name);
                if (elements == null) {
                    elements = new FontData[8];
                    stringToFontData.put(name, elements);
                }
                if (i > elements.length) {
                    FontData[] na = new FontData[i + 8];
                    System.arraycopy(elements, 0, na, 0, elements.length);
                    elements = na;
                    stringToFontData.put(name, elements);
                }
                elements[i] = makeFontData(bundle.getString(key));
            }
        }
    }
