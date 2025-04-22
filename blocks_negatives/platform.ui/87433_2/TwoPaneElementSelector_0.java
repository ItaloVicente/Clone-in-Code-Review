        list.addListener(SWT.Selection, new Listener() {
            @Override
			public void handleEvent(Event evt) {
                handleLowerSelectionChanged();
            }
        });
        list.addListener(SWT.MouseDoubleClick, new Listener() {
            @Override
			public void handleEvent(Event evt) {
                handleDefaultSelected();
            }
        });
        list.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                fQualifierRenderer.dispose();
            }
        });
