		IPropertySource, IWorkbenchAdapter {
	private GroupElement parent;

	private String name;

	private ImageDescriptor imageDescriptor;

	private static ArrayList<PropertyDescriptor> descriptors;
	static {
		descriptors = new ArrayList<>();
		PropertyDescriptor name = new TextPropertyDescriptor(
				IBasicPropertyConstants.P_TEXT, MessageUtil.getString("name")); //$NON-NLS-1$
		descriptors.add(name);
	}

	OrganizationElement(String name, GroupElement parent) {
		this.name = name;
		this.parent = parent;
	}

	public void delete() {
		parent.delete(this);
	}

	@SuppressWarnings("unchecked")
