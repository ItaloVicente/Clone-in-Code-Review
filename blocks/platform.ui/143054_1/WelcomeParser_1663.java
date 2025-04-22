				String qName) throws SAXException {
			if (localName.equals(TAG_INTRO)) {
				introItem = constructWelcomeItem();
				parser.getXMLReader().setContentHandler(parent);
			} else if (localName.equals(TAG_PARAGRAPH)) {
				wrapRanges.add(new int[] { wrapStart, offset - wrapStart });
			}
		}
	}

	public WelcomeParser() throws ParserConfigurationException, SAXException,
			FactoryConfigurationError {
		super();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setFeature("http://xml.org/sax/features/namespaces", true); //$NON-NLS-1$
		parser = factory.newSAXParser();

		parser.getXMLReader().setContentHandler(this);
		parser.getXMLReader().setDTDHandler(this);
		parser.getXMLReader().setEntityResolver(this);
		parser.getXMLReader().setErrorHandler(this);
	}

	public WelcomeItem getIntroItem() {
		return introItem;
	}

	public WelcomeItem[] getItems() {
		return items.toArray(new WelcomeItem[items.size()]);
	}

	public String getTitle() {
		return title;
	}

	public boolean isFormatWrapped() {
		return FORMAT_WRAP.equals(format);
	}

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

	@Override
