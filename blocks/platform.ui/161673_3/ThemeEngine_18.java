		private void sendThemeChangeEvent(boolean restore) {
			EventAdmin eventAdmin = getEventAdmin();
			if (eventAdmin == null) {
				return;
			}
			Map<String, Object> data = new HashMap<>();
			data.put(IThemeEngine.Events.THEME_ENGINE, this);
			data.put(IThemeEngine.Events.THEME, currentTheme);
			data.put(IThemeEngine.Events.DEVICE, display);
			data.put(IThemeEngine.Events.RESTORE, restore);
			Event event = new Event(IThemeEngine.Events.THEME_CHANGED, data);
			eventAdmin.sendEvent(event); // synchronous
		}
