		folder.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.removeListener(SWT.Resize, resizeListener);
			}
		});
