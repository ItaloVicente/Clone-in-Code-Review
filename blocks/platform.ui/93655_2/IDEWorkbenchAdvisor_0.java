		refreshFromLocal();
		activateProxyService();
		((Workbench) PlatformUI.getWorkbench()).registerService(ISelectionConversionService.class,
				new IDESelectionConversionService());

		initializeSettingsChangeListener();
		Display.getCurrent().addListener(SWT.Settings, settingsChangeListener);
