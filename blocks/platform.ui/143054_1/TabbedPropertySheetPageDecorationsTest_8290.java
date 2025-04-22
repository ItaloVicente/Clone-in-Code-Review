		}
	}

	private void setSelection(TreeNode[] selectedNodes) {
		StructuredSelection selection = new StructuredSelection(selectedNodes);
		decorationTestsView.getViewer().setSelection(selection, true);
