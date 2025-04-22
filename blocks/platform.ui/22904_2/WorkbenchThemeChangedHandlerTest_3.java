	public void testResetThemeRegistries() throws Exception {
		FontData[] fontData = new FontData[0];
		RGB rgb = new RGB(255, 0, 0);
		
		FontDefinition fontDefinition1 = mock(FontDefinition.class);
		doReturn("fontDefinition1").when(fontDefinition1).getId();
		doReturn(true).when(fontDefinition1).isOverridden();
		doReturn(null).when(fontDefinition1).getValue();
		
		FontDefinition fontDefinition2 = mock(FontDefinition.class);
		doReturn("fontDefinition2").when(fontDefinition2).getId();
		doReturn(true).when(fontDefinition2).isOverridden();	
		doReturn(fontData).when(fontDefinition2).getValue();
		
		FontDefinition fontDefinition3 = mock(FontDefinition.class);
		doReturn("fontDefinition3").when(fontDefinition2).getId();
		doReturn(false).when(fontDefinition3).isOverridden();
		
		ColorDefinition colorDefinition1 = mock(ColorDefinition.class);
		doReturn("colorDefinition1").when(colorDefinition1).getId();
		doReturn(false).when(colorDefinition1).isOverridden();
		
		ColorDefinition colorDefinition2 = mock(ColorDefinition.class);
		doReturn("colorDefinition2").when(colorDefinition2).getId();
		doReturn(true).when(colorDefinition2).isOverridden();
		doReturn(rgb).when(colorDefinition2).getValue();
		
		ColorDefinition colorDefinition3 = mock(ColorDefinition.class);
		doReturn("colorDefinition3").when(colorDefinition3).getId();
		doReturn(true).when(colorDefinition3).isOverridden();
		doReturn(null).when(colorDefinition3).getValue();
		
		ThemeRegistry themeRegistry = spy(new ThemeRegistry());
		doReturn(new FontDefinition[]{fontDefinition1, fontDefinition2, fontDefinition3}).when(themeRegistry).getFonts();
		doReturn(new ColorDefinition[] {colorDefinition1, colorDefinition2, colorDefinition3}).when(themeRegistry).getColors();
		
		FontRegistry fontRegistry = mock(FontRegistry.class);
		
		ColorRegistry colorRegistry = mock(ColorRegistry.class);
		
		WorkbenchThemeChangedHandlerTestable handler = spy(new WorkbenchThemeChangedHandlerTestable());
		
		
		handler.resetThemeRegistries(themeRegistry, fontRegistry, colorRegistry);

		verify(fontDefinition1, times(1)).isOverridden();
		verify(fontDefinition1, times(1)).resetToDefaultValue();
		verify(fontRegistry, times(1)).put(fontDefinition1.getId(), WorkbenchThemeManager.EMPRY_FONT_DATA_VALUE);
		
		verify(fontDefinition2, times(1)).isOverridden();
		verify(fontDefinition2, times(1)).resetToDefaultValue();
		verify(fontRegistry, times(2)).put(fontDefinition2.getId(), fontData);
		
		verify(fontDefinition3, times(1)).isOverridden();
		verify(fontDefinition3, never()).resetToDefaultValue();
		verify(fontRegistry, never()).put(eq(fontDefinition3.getId()), any(FontData[].class));
		
		verify(colorDefinition1, times(1)).isOverridden();
		verify(colorDefinition1, never()).resetToDefaultValue();
		verify(colorRegistry, never()).put(eq(colorDefinition1.getId()), any(RGB.class));		
		
		verify(colorDefinition2, times(1)).isOverridden();
		verify(colorDefinition2, times(1)).resetToDefaultValue();
		verify(colorRegistry, times(1)).put(colorDefinition2.getId(), rgb);
		
		verify(colorDefinition3, times(1)).isOverridden();
		verify(colorDefinition3, times(1)).resetToDefaultValue();
		verify(colorRegistry, times(1)).put(colorDefinition3.getId(), WorkbenchThemeManager.EMPTY_COLOR_VALUE);
	}
	
