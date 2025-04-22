		parent.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (titleAreaColor != null) {
					titleAreaColor.dispose();
				}
