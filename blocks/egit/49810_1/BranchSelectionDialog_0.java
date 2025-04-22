		if (multiMode) {
			ok.setEnabled(
					fTree.getCheckboxTreeViewer().getCheckedLeafCount() > 0);
		} else {
			ok.setEnabled(!branchesList.getSelection().isEmpty());
		}
