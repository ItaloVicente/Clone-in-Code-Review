				FileBasedConfig fileConfig = (FileBasedConfig) editableConfig;
				File configFile = fileConfig.getFile();
				if (configFile != null) {
					if (isWriteable(configFile))
						location.setText(configFile.getPath());
					else
						location.setText(NLS.bind(UIText.ConfigurationEditorComponent_ReadOnlyLocationFormat,
								configFile.getPath()));
				} else {
					location.setText(UIText.ConfigurationEditorComponent_NoConfigLocationKnown);
				}
