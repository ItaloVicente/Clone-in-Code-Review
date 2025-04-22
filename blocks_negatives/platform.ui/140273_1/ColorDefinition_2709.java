        this.defaultsTo = defaultsTo;
        this.rawValue = value;
        this.isEditable = isEditable;
        this.pluginId = pluginId;
    }

    /**
     * Create a new instance of the receiver.
     *
     * @param original the original definition.  This will be used to populate
     * all fields except defaultsTo and value.  defaultsTo will always be
     * <code>null</code>.
     * @param value the RGB value
     */
    public ColorDefinition(ColorDefinition original, RGB value) {
		super(original.getId(), original.getName(), original.getDescription(), original
				.getCategoryId());
        this.isEditable = original.isEditable();
        this.pluginId = original.getPluginId();
        this.parsedValue = value;
    }

    /**
     * @return the defaultsTo value, or <code>null</code> if none was supplied.
     */
    @Override
