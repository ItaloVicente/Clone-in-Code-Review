            String qName, Attributes atts) throws SAXException {
        if (localName.equals(TAG_WELCOME_PAGE)) {
            WelcomeContentHandler h = new WelcomePageHandler(atts
                    .getValue(ATT_TITLE));
            format = atts.getValue(ATT_FORMAT);
            h.setParent(this);
            parser.getXMLReader().setContentHandler(h);
        }
    }
