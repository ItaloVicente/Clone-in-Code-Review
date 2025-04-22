		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(fontRegistry), eq(fontDefinition1), any(IPreferenceStore.class));
		verify(handler, never()).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(fontRegistry), eq(fontDefinition2), any(IPreferenceStore.class));
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(colorRegistry), eq(colorDefinition), any(IPreferenceStore.class));
		
		verify(stylingEngine, times(1)).style(fontDefinition2);
		verify(stylingEngine, times(1)).style(colorDefinition);
		
