
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
	}
