		SystemReader.setInstance(systemReader);

		mockSystemConfig = mock(FileBasedConfig.class);
		mockUserConfig = mock(FileBasedConfig.class);
		when(systemReader.openSystemConfig(any(), any()))
				.thenReturn(mockSystemConfig);
		when(systemReader.openUserConfig(any(), any()))
				.thenReturn(mockUserConfig);

		when(mockSystemConfig.getString(ConfigConstants.CONFIG_CORE_SECTION,
				null, ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION))
						.thenReturn(null);
