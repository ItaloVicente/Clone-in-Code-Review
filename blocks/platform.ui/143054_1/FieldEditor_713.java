			label.addDisposeListener(event -> label = null);
		} else {
			checkParent(label, parent);
		}
		return label;
	}

	public String getLabelText() {
		return labelText;
	}

	public abstract int getNumberOfControls();

	public String getPreferenceName() {
		return preferenceName;
	}

	@Deprecated
