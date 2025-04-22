			throw new Error(e.getMessage());
		}
	}

	public XMLMemento(Document document, Element element) {
		super();
		this.factory = document;
		this.element = element;
	}
