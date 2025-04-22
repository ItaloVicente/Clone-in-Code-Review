                String qName) throws SAXException {
            if (localName.equals(TAG_ITEM)) {
                items.add(constructWelcomeItem());
                parser.getXMLReader().setContentHandler(parent);
            } else if (localName.equals(TAG_PARAGRAPH)) {
                wrapRanges.add(new int[] { wrapStart, offset - wrapStart });
            }
        }
    }

    private class IntroItemHandler extends ItemHandler {
        @Override
