		@Override
		public StyledString getStyledText(Object element) {
			String decorated = getText(element);
			StyledString label = labelProvider.getStyledText(element);
			return StyledCellLabelProvider.styleDecoratedString(decorated,
					StyledString.DECORATIONS_STYLER, label);
