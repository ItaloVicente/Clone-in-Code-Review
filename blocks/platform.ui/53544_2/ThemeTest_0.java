		themeListenerRegistration = context.registerService(EventHandler.class, event -> {
			ITheme theme = (ITheme)event.getProperty(IThemeEngine.Events.THEME);
			success[0] = IThemeEngine.Events.THEME_CHANGED.equals(event.getTopic())
					&& theme != null
					&& theme.getId().equals("test")
					&& event.getProperty(IThemeEngine.Events.DEVICE) == display
					&& event.getProperty(IThemeEngine.Events.THEME_ENGINE) == themer
					&& event.getProperty(IThemeEngine.Events.RESTORE) == Boolean.TRUE;
		}, properties);
