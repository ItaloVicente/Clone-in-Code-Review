		final DecorationResult decoration = new DecorationResult();
		decorationHelper.decorate(decoration, c);

		final StyledString styled = new StyledString();
		final String prefix = decoration.getPrefix();
		final String suffix = decoration.getSuffix();
		if (prefix != null)
			styled.append(prefix, StyledString.DECORATIONS_STYLER);
