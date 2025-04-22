		viewer.addDoubleClickListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event
					.getSelection();
			Object selectedNode = selection.getFirstElement();
			viewer.setExpandedState(selectedNode,
					!viewer.getExpandedState(selectedNode));
		});
