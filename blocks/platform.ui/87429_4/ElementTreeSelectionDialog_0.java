        BusyIndicator.showWhile(null, () -> {
		    access$superCreate();
		    fViewer.setSelection(new StructuredSelection(
		            getInitialElementSelections()), true);
		    updateOKStatus();
		});
