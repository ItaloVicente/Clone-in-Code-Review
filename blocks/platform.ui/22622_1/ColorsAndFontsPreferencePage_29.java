				if (element instanceof ThemeElementDefinition) {
					ThemeElementDefinition definition = (ThemeElementDefinition) element;
					if (definition.isOverridden() || definition.isAddedByCss()
							|| !isAvailableInCurrentTheme(definition)) {
						return;
					}
					if (element instanceof FontDefinition) {
						editFont(tree.getDisplay());
					} else if (element instanceof ColorDefinition) {
						editColor(tree.getDisplay());
					}
					updateControls();
				}
