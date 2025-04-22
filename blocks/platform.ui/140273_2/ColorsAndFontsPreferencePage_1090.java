		for (Iterator i = previewSet.iterator(); i.hasNext();) {
			IThemePreview preview = (IThemePreview) i.next();
			try {
				preview.dispose();
			} catch (RuntimeException e) {
				WorkbenchPlugin.log(RESOURCE_BUNDLE.getString("errorDisposePreviewLog"), //$NON-NLS-1$
						StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
			}
		}
		previewSet.clear();
	}

	private ColorDefinition getColorAncestor(ColorDefinition definition) {
		String defaultsTo = definition.getDefaultsTo();
		if (defaultsTo == null)
