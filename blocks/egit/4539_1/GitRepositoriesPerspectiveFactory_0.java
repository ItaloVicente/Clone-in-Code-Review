		IFolderLayout bottom = layout.createFolder(
				"bottom", IPageLayout.BOTTOM, (float) 0.5, //$NON-NLS-1$
				layout.getEditorArea());

		bottom.addView(IPageLayout.ID_PROP_SHEET);
		bottom.addView(IHistoryView.VIEW_ID);
		bottom.addView(StagingView.VIEW_ID);
		bottom.addView(ReflogView.VIEW_ID);
