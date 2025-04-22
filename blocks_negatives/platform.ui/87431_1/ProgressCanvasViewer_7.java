        control.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent event) {
                handleDispose(event);
            }
        });
