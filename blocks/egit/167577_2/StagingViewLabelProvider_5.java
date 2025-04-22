		DecorationResult decoration = new DecorationResult();
		decorationHelper.decorate(decoration, stagingEntry);
		String prefix = decoration.getPrefix();
		String suffix = decoration.getSuffix();
		StringBuilder label = new StringBuilder();
		if (prefix != null) {
			label.append(prefix);
		}
