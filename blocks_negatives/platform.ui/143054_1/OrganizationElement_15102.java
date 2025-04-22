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

    /**
     * Constructor.
     * Creates a new OrganizationElement within the passed parent GroupElement,
     *
     * @param name the name
     * @param parent the parent
     */
    OrganizationElement(String name, GroupElement parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * Deletes this OrganizationElement from its parentGroup
     */
    public void delete() {
        parent.delete(this);
    }

    @SuppressWarnings("unchecked")
