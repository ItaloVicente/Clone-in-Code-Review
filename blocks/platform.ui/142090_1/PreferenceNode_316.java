	private IPreferencePage page;

	private List<IPreferenceNode> subNodes;

	private String classname;

	private String id;

	private String label;

	private ImageDescriptor imageDescriptor;

	private Image image;

	public PreferenceNode(String id) {
		Assert.isNotNull(id);
		this.id = id;
	}

	public PreferenceNode(String id, String label, ImageDescriptor image,
			String className) {
		this(id);
		this.imageDescriptor = image;
		Assert.isNotNull(label);
		this.label = label;
		this.classname = className;
	}

	public PreferenceNode(String id, IPreferencePage preferencePage) {
		this(id);
		Assert.isNotNull(preferencePage);
		page = preferencePage;
	}

	@Override
