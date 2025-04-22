		verify(handler, times(1)).populateDefinition(isNull(), isNull(), eq(fontRegistry), eq(fontDefinition1),
				any(IPreferenceStore.class));
		verify(handler, times(1)).populateDefinition(isNull(), isNull(), eq(fontRegistry), eq(fontDefinition2),
				any(IPreferenceStore.class));
		verify(handler, times(1)).populateDefinition(isNull(), isNull(), eq(colorRegistry), eq(colorDefinition1),
				any(IPreferenceStore.class));
		verify(handler, times(1)).populateDefinition(isNull(), isNull(), eq(colorRegistry), eq(colorDefinition2),
				any(IPreferenceStore.class));
