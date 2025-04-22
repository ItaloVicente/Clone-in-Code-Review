		verify(fontRegistry, times(2)).put(eq("fontDefinition1"), any(FontData[].class));
		verify(fontRegistry, atLeast(1)).put(eq("fontDefinition1"), eq(EMPRY_FONT_DATA_VALUE));
		verify(fontRegistry, times(2)).put(eq("fontDefinition2"), any(FontData[].class));
		verify(fontRegistry, atLeast(1)).put(eq("fontDefinition2"), eq(EMPRY_FONT_DATA_VALUE));
		verify(colorRegistry, times(2)).put(eq("colorDefinition1"), any(RGB.class));
		verify(colorRegistry, atLeast(1)).put(eq("colorDefinition1"), eq(EMPTY_COLOR_VALUE));
		verify(colorRegistry, times(2)).put(eq("colorDefinition2"), any(RGB.class));
		verify(colorRegistry, atLeast(1)).put(eq("colorDefinition2"), eq(EMPTY_COLOR_VALUE));
		
		verify(handler, times(1)).sendThemeRegistryRestyledEvent();
