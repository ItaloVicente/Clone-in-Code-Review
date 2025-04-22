
		IEventBroker broker = appContext.get(IEventBroker.class);
		if (broker != null) {
			Map<String, Object> data = null;
			if (themeEngineForEvent != null) {
				data = new HashMap<String, Object>();
				data.put(IThemeEngine.Events.THEME_ENGINE, themeEngineForEvent);
				data.put(IThemeEngine.Events.THEME,
						themeEngineForEvent.getActiveTheme());
				data.put(IThemeEngine.Events.DEVICE, display);
				data.put(IThemeEngine.Events.RESTORE, false);
			}
			broker.send(IThemeEngine.Events.THEME_CHANGED, data);
			broker.send(UIEvents.UILifeCycle.THEME_CHANGED, null);
		}
