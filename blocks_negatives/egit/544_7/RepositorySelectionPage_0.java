		ControlDecoration dec = new ControlDecoration(uriTextField, SWT.TOP
				| SWT.LEFT);

		dec.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(
				FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage());

		dec.setShowOnlyOnFocus(true);
		dec.setShowHover(true);

		dec
				.setDescriptionText(UIText.RepositorySelectionPage_ShowPreviousURIs_HoverText);
