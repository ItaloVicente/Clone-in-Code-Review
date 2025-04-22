	@Override
	public void update(Object element, String[] properties) {

		ViewerFilter[] filters = getFilters();
		if (filters != null) {

			if (properties == null) {
				properties = new String[] { "" }; //$NON-NLS-1$
			}

			if (findItems(element).length > 0) {
				super.update(element, properties);
			} else {
				super.add(element);
			}

		} else {
			super.update(element, properties);
		}
	}

