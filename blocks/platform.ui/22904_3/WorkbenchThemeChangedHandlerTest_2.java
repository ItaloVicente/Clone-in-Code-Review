		
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(fontRegistry), eq(fontDefinition), any(IPreferenceStore.class));
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(colorRegistry), eq(colorDefinition), any(IPreferenceStore.class));
		
		verify(handler, times(1)).resetThemeRegistries(themeRegistry, fontRegistry, colorRegistry);
		
		verify(handler, times(1)).sendThemeRegistryRestyledEvent();
