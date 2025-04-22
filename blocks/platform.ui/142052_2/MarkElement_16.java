	private String headingName;

	private IAdaptable parent;

	private int offset;

	private int numberOfLines;

	private int length;

	private ArrayList<MarkElement> children;

	public MarkElement(IAdaptable parent, String heading, int offset, int length) {
		this.parent = parent;
		if (parent instanceof MarkElement) {
			((MarkElement) parent).addChild(this);
		}
		this.headingName = heading;
		this.offset = offset;
		this.length = length;
	}

	private void addChild(MarkElement child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}

	@SuppressWarnings("unchecked")
