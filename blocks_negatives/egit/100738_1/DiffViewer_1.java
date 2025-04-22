		getControl().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				EditorsUI.getPreferenceStore()
						.removePropertyChangeListener(editorPrefListener);
				PlatformUI.getWorkbench().getThemeManager()
						.removePropertyChangeListener(themeListener);
				colors.dispose();
			}
		});
