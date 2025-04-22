		this.disposeListener = new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if( viewer.isCellEditorActive() ) {
					cancelEditing();
				}
