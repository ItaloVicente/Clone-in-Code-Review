
		/**
		 * Returns the DEFINITION_NOT_AVAIL_COLOR color when definition is not
		 * present in the current theme or null when it is available
		 * 
		 * @param def
		 *            the definition
		 * @return the DEFINITION_NOT_AVAIL_COLOR color or null
		 */
		@Override
		public Color getForeground(Object element) {
			if (element instanceof ThemeElementDefinition && !isAvailableInCurrentTheme((ThemeElementDefinition) element)) {
				return tree.getDisplay().getSystemColor(DEFINITION_NOT_AVAIL_COLOR);
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.
		 * Object)
		 */
		@Override
		public Color getBackground(Object element) {
			return null;
		}
