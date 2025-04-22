
        for (Iterator i = fontPreferencesToSet.keySet().iterator(); i.hasNext();) {
            String id = (String) i.next();
            String key = ThemeElementHelper.createPreferenceKey(currentTheme, id);
            FontData[] fd = (FontData[]) fontPreferencesToSet.get(id);

            String fdString = PreferenceConverter.getStoredRepresentation(fd);
