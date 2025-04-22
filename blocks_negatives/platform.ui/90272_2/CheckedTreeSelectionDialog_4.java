        listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                fViewer.setCheckedElements(new Object[0]);
                updateOKStatus();
            }
        };
