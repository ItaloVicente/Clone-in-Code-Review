                String qName) throws SAXException {
            if (localName.equals(TAG_INTRO)) {
                introItem = constructWelcomeItem();
                parser.getXMLReader().setContentHandler(parent);
            } else if (localName.equals(TAG_PARAGRAPH)) {
                wrapRanges.add(new int[] { wrapStart, offset - wrapStart });
            }
        }
    }

    /**
     * Creates a new welcome parser.
     */
    public WelcomeParser() throws ParserConfigurationException, SAXException,
            FactoryConfigurationError {
        super();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        parser = factory.newSAXParser();

        parser.getXMLReader().setContentHandler(this);
        parser.getXMLReader().setDTDHandler(this);
        parser.getXMLReader().setEntityResolver(this);
        parser.getXMLReader().setErrorHandler(this);
    }

    /**
     * Returns the intro item.
     */
    public WelcomeItem getIntroItem() {
        return introItem;
    }

    /**
     * Returns the items.
     */
    public WelcomeItem[] getItems() {
        return items.toArray(new WelcomeItem[items.size()]);
    }

    /**
     * Returns the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns whether or not the welcome editor input should be wrapped.
     */
    public boolean isFormatWrapped() {
        return FORMAT_WRAP.equals(format);
    }

    /**
     * Parse the contents of the input stream
     */
    public void parse(InputStream is) {
        try {
            parser.parse(new InputSource(is), this);
        } catch (SAXException e) {
            IStatus status = new Status(IStatus.ERROR,
                    IDEWorkbenchPlugin.IDE_WORKBENCH, 1, IDEWorkbenchMessages.WelcomeParser_parseException, e);
            IDEWorkbenchPlugin.log(IDEWorkbenchMessages.WelcomeParser_parseError, status);
        } catch (IOException e) {
            IStatus status = new Status(IStatus.ERROR,
                    IDEWorkbenchPlugin.IDE_WORKBENCH, 1, IDEWorkbenchMessages.WelcomeParser_parseException, e);
            IDEWorkbenchPlugin.log(IDEWorkbenchMessages.WelcomeParser_parseError, status);
        }
    }

    /**
     * Handles the start element
     */
    @Override
