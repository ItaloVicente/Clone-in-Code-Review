    /**
     * The property id.
     */
    private Object id;

    /**
     * The name to display for the property.
     */
    private String display;

    /**
     * Category name, or <code>null</code> if none (the default).
     */
    private String category = null;

    /**
     * Description of the property, or <code>null</code> if none (the default).
     */
    private String description = null;

    /**
     * The help context ids, or <code>null</code> if none (the default).
     */
    private Object helpIds;

    /**
     * The flags used to filter the property.
     */
    private String[] filterFlags;

    /**
     * The object that provides the property value's text and image, or
     * <code>null</code> if the default label provider is used (the default).
     */
    private ILabelProvider labelProvider = null;

    /**
     * The object to validate the values in the cell editor, or <code>null</code>
     * if none (the default).
     */
    private ICellEditorValidator validator;

    /**
     * Indicates if the descriptor is compatible with other descriptors of this
     * type. <code>false</code> by default.
     */
    private boolean incompatible = false;

    /**
     * Creates a new property descriptor with the given id and display name
     * @param id
     * @param displayName
     */
    public PropertyDescriptor(Object id, String displayName) {
        Assert.isNotNull(id);
        Assert.isNotNull(displayName);
        this.id = id;
        this.display = displayName;
    }

    /**
     * The <code>PropertyDescriptor</code> implementation of this
     * <code>IPropertyDescriptor</code> method returns <code>null</code>.
     * <p>
     * Since no cell editor is returned, the property is read only.
     * </p>
     */
    @Override
