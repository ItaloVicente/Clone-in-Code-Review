	@Override
	protected String getFilterString() {
		String filterString = super.getFilterString();
		if (filterString.indexOf("*") != 0 && filterString.indexOf("?") != 0 //$NON-NLS-1$ //$NON-NLS-2$
				&& filterString.indexOf(".") != 0) {//$NON-NLS-1$
			filterString = "*" + filterString; //$NON-NLS-1$
		}
		return filterString;
	}

