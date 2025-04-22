		/**
		 * Apply the fonts and colors to the control if
		 * required.
		 * @param control
		 */
		public void applyFontsAndColors(TableTreeItem control) {
			if(usedDecorators){
				if(background != null) {
					control.setBackground(background);
				}

				if(foreground != null) {
					control.setForeground(foreground);
				}

				if(font != null) {
					control.setFont(font);
				}
			}
			clear();
		}

