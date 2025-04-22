	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return UIIcons.CHANGESET;
	}

	public String getLabel(Object o) {
		return abbreviate();
	}

	public Object getParent(Object o) {
		return null;
	}

