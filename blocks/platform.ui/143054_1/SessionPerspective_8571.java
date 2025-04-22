		String editorArea = layout.getEditorArea();
		layout
				.addView(SessionView.VIEW_ID, IPageLayout.LEFT, 0.33f,
						editorArea);
		layout.addPlaceholder(MockViewPart.ID4, IPageLayout.RIGHT, .033f,
				editorArea);
	}
