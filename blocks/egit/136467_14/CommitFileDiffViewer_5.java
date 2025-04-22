	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		copyAll.setEnabled(
				getContentProvider() instanceof IStructuredContentProvider
						&& doGetItemCount() > 0);
	}

