		callTrace.add("isDirty");
		return dirty;
	}

	public void setDirty(boolean value) {
		dirty = value;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
