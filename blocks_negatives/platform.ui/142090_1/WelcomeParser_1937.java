                String qName, Attributes atts) throws SAXException {
            if (localName.equals(TAG_INTRO)) {
                ItemHandler h = new IntroItemHandler();
                h.setParent(WelcomePageHandler.this);
                parser.getXMLReader().setContentHandler(h);
            } else if (localName.equals(TAG_ITEM)) {
                ItemHandler h = new ItemHandler();
                h.setParent(WelcomePageHandler.this);
                parser.getXMLReader().setContentHandler(h);
            }
        }
    }

    private class ItemHandler extends WelcomeContentHandler {
