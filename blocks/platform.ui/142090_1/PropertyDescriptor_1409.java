		}

		return true;
	}

	public void setAlwaysIncompatible(boolean flag) {
		incompatible = flag;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFilterFlags(String value[]) {
		filterFlags = value;
	}

	public void setHelpContextIds(Object contextIds) {
		helpIds = contextIds;
	}

	public void setLabelProvider(ILabelProvider provider) {
		labelProvider = provider;
	}

	public void setValidator(ICellEditorValidator validator) {
		this.validator = validator;
	}
