		textWidget.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent disposeEvent) {
				showWhitespaceAction.dispose();
			}
		});
