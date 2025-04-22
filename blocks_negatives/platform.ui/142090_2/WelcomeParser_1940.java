                    String qName) throws SAXException {
                if (localName.equals(TAG_TOPIC)) {
                    helpRanges.add(new int[] { textStart, offset - textStart });
                    parser.getXMLReader().setContentHandler(parent);
                }
            }
        }

        protected WelcomeItem constructWelcomeItem() {
            if (isFormatWrapped()) {
                for (int i = 0; i < wrapRanges.size(); i++) {
                    int[] range = wrapRanges.get(i);
                    int start = range[0];
                    int length = range[1];
                    for (int j = start; j < start + length; j++) {
                        char ch = text.charAt(j);
                        if (ch == DELIMITER) {
                            text.replace(j, j + 1, " "); //$NON-NLS-1$
                        }
                    }
                }
            }
            return new WelcomeItem(
                    text.toString(),
                    boldRanges.toArray(new int[boldRanges.size()][2]),
                    actionRanges
                            .toArray(new int[actionRanges.size()][2]),
                    pluginIds.toArray(new String[pluginIds.size()]),
                    classes.toArray(new String[classes.size()]),
                    helpRanges.toArray(new int[helpRanges.size()][2]),
                    helpIds.toArray(new String[helpIds.size()]),
                    helpHrefs.toArray(new String[helpHrefs.size()]));
        }

        @Override
