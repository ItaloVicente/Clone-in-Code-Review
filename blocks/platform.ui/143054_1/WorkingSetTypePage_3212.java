		super.dispose();
	}

	public String getSelection() {
		WorkingSetDescriptor descriptor = getSelectedWorkingSet();
		if (descriptor != null)
			return descriptor.getId();

		return null;
	}
