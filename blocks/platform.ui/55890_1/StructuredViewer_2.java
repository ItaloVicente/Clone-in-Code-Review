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

