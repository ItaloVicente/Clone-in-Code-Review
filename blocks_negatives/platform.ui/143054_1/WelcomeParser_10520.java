                    String qName) throws SAXException {
                if (localName.equals(TAG_ACTION)) {
                    actionRanges
                            .add(new int[] { textStart, offset - textStart });
                    parser.getXMLReader().setContentHandler(parent);
                }
            }
        }

        private class TopicHandler extends WelcomeContentHandler {
            public TopicHandler(String helpId, String href) {
                helpIds.add(helpId);
                helpHrefs.add(href);
            }

            @Override
