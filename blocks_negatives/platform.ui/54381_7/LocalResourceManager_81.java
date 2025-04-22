        owner.addDisposeListener(new DisposeListener() {
	        @Override
			public void widgetDisposed(DisposeEvent e) {
	            LocalResourceManager.this.dispose();
	        }
        });
