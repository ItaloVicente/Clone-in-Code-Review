
		repositoryUtil.getPreferences().removePreferenceChangeListener(
				configurationListener);

		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.removePostSelectionListener(selectionChangedListener);

