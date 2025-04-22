		eventHandler = new EventHandler() {

			@Override
			public void handleEvent(final Event event) {
				if (event == null)
					return;
				ITheme theme = (ITheme) event.getProperty("theme");
				Display display = (Display) event.getProperty(IThemeEngine.Events.DEVICE);

				display.asyncExec(new Runnable() {

					@Override
					public void run() {
						OS.setDarkThemePreferred(isDark);
					}
				});
