		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (disabledImage != null)
					disabledImage.dispose();
			}
