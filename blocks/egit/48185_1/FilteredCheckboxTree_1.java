	@Override
	protected String getFilterString() {
		String filterString = super.getFilterString();
		if (!filterText.getText().equals(initialText)
				&& filterString.indexOf("*") != 0 //$NON-NLS-1$
				&& filterString.indexOf("?") != 0 //$NON-NLS-1$
				&& filterString.indexOf(".") != 0) {//$NON-NLS-1$
			filterString = "*" + filterString; //$NON-NLS-1$
		}
		return filterString;
	}

