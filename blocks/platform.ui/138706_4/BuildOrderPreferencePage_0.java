				Text text = getTextControl();
				if (text != null) {
					IEclipsePreferences conf = ConfigurationScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
					IEclipsePreferences def = DefaultScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
					int value = conf.getInt(ResourcesPlugin.PREF_MAX_BUILD_ITERATIONS,
							def.getInt(ResourcesPlugin.PREF_MAX_BUILD_ITERATIONS, 10));
					text.setText(Integer.toString(value));
				}
				valueChanged();
			}
