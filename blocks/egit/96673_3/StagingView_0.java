		viewer.addDoubleClickListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = selection.getFirstElement();
			if (selectedNode instanceof StagingFolderEntry) {
				viewer.setExpandedState(selectedNode,
						!viewer.getExpandedState(selectedNode));
			}
		});
