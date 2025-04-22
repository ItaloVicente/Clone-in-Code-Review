        if (key instanceof ModifierKey) {
            String formattedName = Util.translateString(RESOURCE_BUNDLE, key
                    .toString(), null, false, false);
            if (formattedName != null) {
                return formattedName;
            }
        }
