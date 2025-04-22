						if (set instanceof PluginActionSet) {
							PluginActionSet pluginActionSet = (PluginActionSet) set;
							sets.add(pluginActionSet);
						} else {
							String pattern = "Ignored unexpected IActionSet implementation for descriptor {0}: {1}"; //$NON-NLS-1$
							WorkbenchPlugin.log(NLS.bind(pattern, desc.getId(), set));
						}

