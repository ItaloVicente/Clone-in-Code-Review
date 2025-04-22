		this.defaultsTo = defaultsTo;
		this.rawValue = value;
		this.isEditable = isEditable;
		this.pluginId = pluginId;
	}

	public ColorDefinition(ColorDefinition original, RGB value) {
		super(original.getId(), original.getName(), original.getDescription(), original.getCategoryId());
		this.isEditable = original.isEditable();
		this.pluginId = original.getPluginId();
		this.parsedValue = value;
	}

	@Override
