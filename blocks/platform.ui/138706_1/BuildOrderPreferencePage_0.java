				Text text = getTextControl();
				if (text != null) {
					IEclipsePreferences node = DefaultScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
					int value = node.getInt(ResourcesPlugin.PREF_MAX_BUILD_ITERATIONS, 10);
					text.setText(Integer.toString(value));
				}
				valueChanged();
			}
