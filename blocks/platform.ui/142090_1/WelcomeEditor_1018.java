		return false;
	}

	public void read(InputStream is) throws IOException {
		try {
			parser = new WelcomeParser();
		} catch (ParserConfigurationException e) {
			throw (IOException) (new IOException().initCause(e));
		} catch (SAXException e) {
			throw (IOException) (new IOException().initCause(e));
		}
		parser.parse(is);
	}

	public void readFile() {
		URL url = ((WelcomeEditorInput) getEditorInput()).getAboutInfo()
				.getWelcomePageURL();

		if (url == null) {
