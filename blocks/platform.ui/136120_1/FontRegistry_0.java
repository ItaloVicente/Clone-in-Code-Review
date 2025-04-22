        FontRecord record = stringToFontRecord.get(JFaceResources.DEFAULT_FONT);
        if (record != null) {
            return record;
        }

        FontData[] fontData = stringToFontData.get(JFaceResources.DEFAULT_FONT);
        if (fontData != null) {
            record = createFont(JFaceResources.DEFAULT_FONT, fontData);
        }
