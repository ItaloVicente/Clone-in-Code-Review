			IActionSet set = pluginActionSets.get(i);
			if (set instanceof PluginActionSet) {
				PluginActionSetBuilder builder = new PluginActionSetBuilder();
				builder.readActionExtensions((PluginActionSet) set, window);
				builders[i] = builder;
			}
