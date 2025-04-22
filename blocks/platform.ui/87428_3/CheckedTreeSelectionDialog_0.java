        BusyIndicator.showWhile(null, () -> {
		    access$superCreate();
		    fViewer.setCheckedElements(getInitialElementSelections()
		            .toArray());
		    if (fExpandedElements != null) {
		        fViewer.setExpandedElements(fExpandedElements);
		    }
		    updateOKStatus();
		});
