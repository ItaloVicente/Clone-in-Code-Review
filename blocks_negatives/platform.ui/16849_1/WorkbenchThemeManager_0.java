			
		if (PlatformUI.getWorkbench().getDisplay() != null) {
			final boolean[] highContrast = new boolean[] { false };
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Runnable#run()
				 */
				public void run() {
					highContrast[0] = Display.getCurrent().getHighContrast();

					Display.getCurrent().addListener(SWT.Settings, new Listener() {
						public void handleEvent(Event event) {
							updateThemes();
						}
					});
				}
			});
			
			if (highContrast[0])
				themeId = SYSTEM_DEFAULT_THEME;
		}
