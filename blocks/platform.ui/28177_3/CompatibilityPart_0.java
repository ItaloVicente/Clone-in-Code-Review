	@PersistState
	void persistState() {
		if (wrapped instanceof IPersistable) {
			try {
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument();
				Element root = document.createElement("dummy-memento"); //$NON-NLS-1$
				document.appendChild(root);
				IMemento memento = new XMLMemento(document, root);
				((IPersistable) wrapped).saveState(memento);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

