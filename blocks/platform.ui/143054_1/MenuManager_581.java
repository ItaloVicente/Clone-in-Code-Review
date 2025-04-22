	public MenuManager() {
		this(null, null, null);
	}

	public MenuManager(String text) {
		this(text, null, null);
	}

	public MenuManager(String text, String id) {
		this(text, null, id);
	}

	public MenuManager(String text, ImageDescriptor image, String id) {
		this.menuText = text;
		this.image = image;
		this.id = id;
	}

	@Override
