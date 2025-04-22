	private QName name;
	private String id;

	private static final long serialVersionUID = 2193425983220679887L;

	public NullPointer(QName name, Locale locale) {
		super(null, locale);
		this.name = name;
	}

	public NullPointer(NodePointer parent, QName name) {
		super(parent);
		this.name = name;
	}

	public NullPointer(Locale locale, String id) {
		super(null, locale);
		this.id = id;
	}

	@Override
