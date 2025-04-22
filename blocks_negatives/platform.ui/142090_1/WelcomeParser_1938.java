                    String qName) throws SAXException {
                if (localName.equals(TAG_BOLD)) {
                    boldRanges.add(new int[] { textStart, offset - textStart });
                    parser.getXMLReader().setContentHandler(parent);
                }
            }
        }

        private class ActionHandler extends WelcomeContentHandler {
            public ActionHandler(String pluginId, String className) {
                pluginIds.add(pluginId);
                classes.add(className);
            }

            @Override
