		String editorArea = layout.getEditorArea();
		IFolderLayout folder1 = layout.createFolder("folder1",
				IPageLayout.LEFT, .25f, editorArea);
		folder1.addView(MockViewPart.ID);
		folder1.addPlaceholder(MockViewPart.ID2);
		layout.addView(MockViewPart.ID3, IPageLayout.RIGHT, .75f, editorArea);
		layout.addPlaceholder(MockViewPart.ID4, IPageLayout.BOTTOM, .75f,
				editorArea);
	}
