		
		addDisposeListener(new DisposeListener() {			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.setData(HANDLE_IMAGE, null);
				e.widget.setData(FRAME_IMAGE, null);
			}
		});
