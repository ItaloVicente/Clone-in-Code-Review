            Display display = tree.getDisplay();
            if (element instanceof FontDefinition) {
                int parentHeight = tree.getViewer().getControl().getFont()
                        .getFontData()[0].getHeight();
                Font baseFont = fontRegistry.get(((FontDefinition) element)
                        .getId());
                Font font = (Font) fonts.get(baseFont);
                if (font == null) {
                    FontData[] data = baseFont.getFontData();
                    for (FontData fontData : data) {
                        fontData.setHeight(parentHeight);
                    }
                    font = new Font(display, data);

                    fonts.put(baseFont, font);
                }
                return font;
