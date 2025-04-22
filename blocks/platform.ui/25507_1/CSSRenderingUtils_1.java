			 }
		};
			
		toFrame.getDisplay().addListener(SWT.Skin, listener);

		imageBasedFrame.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.getDisplay().removeListener(SWT.Skin, listener);
