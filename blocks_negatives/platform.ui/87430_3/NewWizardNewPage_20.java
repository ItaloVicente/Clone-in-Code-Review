        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
            	    IStructuredSelection s = (IStructuredSelection) event
						.getSelection();
				selectionChanged(new SelectionChangedEvent(event.getViewer(), s));

				Object element = s.getFirstElement();
                if (treeViewer.isExpandable(element)) {
                	treeViewer.setExpandedState(element, !treeViewer
                            .getExpandedState(element));
                } else if (element instanceof WorkbenchWizardElement) {
                    page.advanceToNextPageOrFinish();
                }
            }
        });
