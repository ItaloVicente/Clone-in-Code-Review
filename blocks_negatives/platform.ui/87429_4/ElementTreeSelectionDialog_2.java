        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                access$superCreate();
                fViewer.setSelection(new StructuredSelection(
                        getInitialElementSelections()), true);
                updateOKStatus();
            }
        });
