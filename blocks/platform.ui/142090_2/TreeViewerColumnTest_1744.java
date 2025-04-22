		TreeViewer viewer = (TreeViewer) fViewer;
		TableTreeTestLabelProvider provider = (TableTreeTestLabelProvider) viewer
				.getLabelProvider();
		provider.fExtended = true;
		provider.setSuffix("added suffix");
		fViewer.refresh();
		TestElement first = fRootElement.getFirstChild();
		String newLabel = providedString(first);
		assertEquals("rendered label", newLabel, getItemText(0));
		provider.fExtended = false;
		fViewer.refresh();
	}
