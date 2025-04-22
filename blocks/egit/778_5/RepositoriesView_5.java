		});
		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.addPostSelectionListener(selectionChangedListener);
		repositoryUtil.getPreferences().addPreferenceChangeListener(
				configurationListener);
