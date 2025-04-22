		    if (treeViewer.isExpandable(element)) {
		    	treeViewer.setExpandedState(element, !treeViewer
		                .getExpandedState(element));
		    } else if (element instanceof WorkbenchWizardElement) {
		        page.advanceToNextPageOrFinish();
		    }
