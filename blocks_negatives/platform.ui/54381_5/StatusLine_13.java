		fCancelButton.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Image i = fCancelButton.getImage();
				if ((i != null) && (!i.isDisposed())) {
					i.dispose();
				}
