			@Override
			public Table createTable(Composite composite, int defaultOrientation) {
				if (PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS)) {
					redColor = composite.getDisplay().getSystemColor(SWT.COLOR_DARK_RED);
				}
				return super.createTable(composite, defaultOrientation);
			}

