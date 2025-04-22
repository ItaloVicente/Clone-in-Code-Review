	
	public void testOverrideThemeDefinitionsWhenDefinitionModifiedByUser() throws Exception {
		IStylingEngine stylingEngine = mock(IStylingEngine.class);
		
		FontDefinition fontDefinition1 = mock(FontDefinition.class);
		doReturn("fontDefinition1").when(fontDefinition1).getId();
		doReturn(true).when(fontDefinition1).isOverridden();
		doReturn(false).when(fontDefinition1).isModifiedByUser();
		
		FontDefinition fontDefinition2 = mock(FontDefinition.class);
		doReturn("fontDefinition2").when(fontDefinition2).getId();
		doReturn(true).when(fontDefinition2).isOverridden();	
		doReturn(true).when(fontDefinition2).isModifiedByUser();
		
		ColorDefinition colorDefinition1 = mock(ColorDefinition.class);
		doReturn("colorDefinition1").when(colorDefinition1).getId();
		doReturn(true).when(colorDefinition1).isOverridden();
		doReturn(true).when(colorDefinition1).isModifiedByUser();
		
		ColorDefinition colorDefinition2 = mock(ColorDefinition.class);
		doReturn("colorDefinition2").when(colorDefinition2).getId();
		doReturn(true).when(colorDefinition2).isOverridden();	
		doReturn(false).when(colorDefinition2).isModifiedByUser();
		
		ThemeRegistry themeRegistry = spy(new ThemeRegistry());
		doReturn(new FontDefinition[]{fontDefinition1, fontDefinition2}).when(themeRegistry).getFonts();
		doReturn(new ColorDefinition[] {colorDefinition1, colorDefinition2}).when(themeRegistry).getColors();
		
		FontRegistry fontRegistry = mock(FontRegistry.class);
		
		ColorRegistry colorRegistry = mock(ColorRegistry.class);
		
		ThemesExtension themesExtension = mock(ThemesExtension.class); 
		
		WorkbenchThemeChangedHandlerTestable handler = spy(new WorkbenchThemeChangedHandlerTestable());
		doReturn(stylingEngine).when(handler).getStylingEngine();
		doReturn(themeRegistry).when(handler).getThemeRegistry();
		doReturn(fontRegistry).when(handler).getFontRegistry();
		doReturn(colorRegistry).when(handler).getColorRegistry();
		doReturn(themesExtension).when(handler).createThemesExtension();
		
		
		handler.overrideAlreadyExistingDefinitions(mock(Event.class), stylingEngine, themeRegistry, fontRegistry, colorRegistry);
		
		verify(stylingEngine, times(1)).style(fontDefinition1);
		verify(fontRegistry, times(1)).put("fontDefinition1", null);
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(fontRegistry), eq(fontDefinition1), any(IPreferenceStore.class));
		
		verify(stylingEngine, times(1)).style(fontDefinition2);
		verify(fontRegistry, never()).put(eq("fontDefinition2"), any(FontData[].class));
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(fontRegistry), eq(fontDefinition2), any(IPreferenceStore.class));
		
		verify(stylingEngine, times(1)).style(colorDefinition1);
		verify(colorRegistry, never()).put(eq("colorDefinition1"), any(RGB.class));
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(colorRegistry), eq(colorDefinition1), any(IPreferenceStore.class));
		
		verify(stylingEngine, times(1)).style(colorDefinition2);
		verify(colorRegistry, times(1)).put("colorDefinition2", null);
		verify(handler, times(1)).populateDefinition(any(ITheme.class), any(org.eclipse.ui.themes.ITheme.class), 
				eq(colorRegistry), eq(colorDefinition2), any(IPreferenceStore.class));
	}
