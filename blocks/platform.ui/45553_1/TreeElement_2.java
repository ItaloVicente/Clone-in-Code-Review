			Color background = null;
			if ((event.detail & SWT.SELECTED) != 0) {
				background = getSelectionBackgroundColor(parent);

			} else if ((event.detail & SWT.HOT) != 0) {
				background = getHotBackgroundColor(parent);

			}
			if(background == null){
				background = parent.getBackground();
			}
