	private Object id;

	private String display;

	private String category = null;

	private String description = null;

	private Object helpIds;

	private String[] filterFlags;

	private ILabelProvider labelProvider = null;

	private ICellEditorValidator validator;

	private boolean incompatible = false;

	public PropertyDescriptor(Object id, String displayName) {
		Assert.isNotNull(id);
		Assert.isNotNull(displayName);
		this.id = id;
		this.display = displayName;
	}

	@Override
