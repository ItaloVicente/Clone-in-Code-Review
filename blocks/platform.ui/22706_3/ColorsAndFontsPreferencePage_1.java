
	private void updateDefinitionState(ThemeElementDefinition definition, boolean reset) {
		if (reset) {
			definition.removeState(ThemeElementDefinition.State.MODIFIED_BY_USER);
		} else {
			definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
		}
	}

	private void refreshElement(ThemeElementDefinition definition) {
		tree.getViewer().refresh(definition);
		updateTreeSelection(tree.getViewer().getSelection());		
		
		Object newValue = definition instanceof ColorDefinition ? 
			((ColorDefinition) definition).getValue(): ((FontDefinition) definition).getValue();
		cascadingTheme.fire(new PropertyChangeEvent(this, definition.getId(), null, newValue));
	}

	private static class CascadingThemeExt extends CascadingTheme {
		public CascadingThemeExt(ITheme currentTheme, CascadingColorRegistry colorRegistry,
				CascadingFontRegistry fontRegistry) {
			super(currentTheme, colorRegistry, fontRegistry);
		}

		public void fire(PropertyChangeEvent event) {
			super.fire(event);
		}

	}
