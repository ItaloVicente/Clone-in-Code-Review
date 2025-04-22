        SelectionListener listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                Object[] viewerElements = fContentProvider.getElements(fInput);
                if (fContainerMode) {
					fViewer.setCheckedElements(viewerElements);
				} else {
                    for (Object viewerElement : viewerElements) {
						fViewer.setSubtreeChecked(viewerElement, true);
					}
                }
                updateOKStatus();
            }
        };
