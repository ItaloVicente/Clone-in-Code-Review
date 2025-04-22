		String label = internalGetLabel(object);
		if (object instanceof FilterableNode) {
			String filter = ((FilterableNode) object).getFilter();
			if (!StringUtils.isEmptyOrNull(filter)) {
				label += MessageFormat
						.format(UIText.RepositoriesView_FilteredSuffix, filter);
			}
		}
		return label;
	}

	private String internalGetLabel(Object object) {
