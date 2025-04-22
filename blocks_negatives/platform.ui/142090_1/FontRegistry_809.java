        }

        try {
            bundle = ResourceBundle.getBundle(WSLocation);
            readResourceBundle(bundle, WSLocation);
        } catch (MissingResourceException wsException) {
            try {
                bundle = ResourceBundle.getBundle(OSLocation);
                readResourceBundle(bundle, WSLocation);
            } catch (MissingResourceException osException) {
                if (location != OSLocation) {
                    bundle = ResourceBundle.getBundle(location);
                    readResourceBundle(bundle, WSLocation);
                } else {
