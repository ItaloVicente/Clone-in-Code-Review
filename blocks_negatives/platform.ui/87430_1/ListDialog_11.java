        fTableViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                if (fAddCancelButton) {
					okPressed();
				}
            }
        });
