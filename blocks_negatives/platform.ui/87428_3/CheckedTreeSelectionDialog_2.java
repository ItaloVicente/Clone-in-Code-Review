        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                access$superCreate();
                fViewer.setCheckedElements(getInitialElementSelections()
                        .toArray());
                if (fExpandedElements != null) {
                    fViewer.setExpandedElements(fExpandedElements);
                }
                updateOKStatus();
            }
        });
