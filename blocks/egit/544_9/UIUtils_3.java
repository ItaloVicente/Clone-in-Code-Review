	public static void addBulbDecorator(final Control control, final String tooltip) {
		ControlDecoration dec = new ControlDecoration(control, SWT.TOP | SWT.LEFT);

		dec.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(
				FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage());

		dec.setShowOnlyOnFocus(true);
		dec.setShowHover(true);

		dec.setDescriptionText(tooltip);
	}

