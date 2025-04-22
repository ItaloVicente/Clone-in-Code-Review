
	private void setLeftLabel(DiffNode node, String name, boolean fireChange) {
		GitCompareLabelProvider labels = new GitCompareLabelProvider() {

			@Override
			public String getLeftLabel(Object input) {
				return MessageFormat.format(
						UIText.GitCompareFileRevisionEditorInput_LocalLabel,
						name);
			}

			@Override
			public String getRightLabel(Object input) {
				return null; // Use default
			}

			@Override
			public String getAncestorLabel(Object input) {
				return null; // Use default
			}
		};
		getCompareConfiguration().setLabelProvider(node, labels);
		if (fireChange) {
			labels.fireNodeLabelChanged(node);
		}
	}
