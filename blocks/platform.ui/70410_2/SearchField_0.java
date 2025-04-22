		Text text = null;
		if (Util.isWin32() || Util.isGtk()) {
			text = new Text(parent, SWT.NONE);
		} else {
			text = new Text(parent, SWT.SEARCH);
		}

