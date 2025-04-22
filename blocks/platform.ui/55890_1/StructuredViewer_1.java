		@Override
		public void applyFontsAndColors(TableTreeItem control) {

			if(colorProvider == null){
				if(usedDecorators){
					if(background != null) {
						control.setBackground(background);
					}

					if(foreground != null) {
						control.setForeground(foreground);
					}
				}
			}
			else{
				control.setBackground(background);
				control.setForeground(foreground);
			}

			if(fontProvider == null){
				if(usedDecorators && font != null) {
					control.setFont(font);
				}
			} else {
				control.setFont(font);
			}

			clear();
		}


