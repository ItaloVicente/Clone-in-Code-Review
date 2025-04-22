
		@Override
		public Color getForeground(Object element) {
			if (element instanceof ThemeElementDefinition && !isAvailableInCurrentTheme((ThemeElementDefinition) element)) {
				return tree.getDisplay().getSystemColor(DEFINITION_NOT_AVAIL_COLOR);
			}
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			return null;
		}
