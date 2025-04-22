		handler.addOpenListener(new IOpenEventListener() {
			@Override
			public void handleOpen(SelectionEvent e) {
				StructuredViewer.this.handleOpen(e);
			}
		});
