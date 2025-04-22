	protected static Color colorHidden = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);

	protected static void setStyle(ViewerCell cell, int fontStyle, Color foreground) {
		FontData data = cell.getFont().getFontData()[0];
		Font bold = new Font(Display.getCurrent(), data.getName(), data.getHeight(), fontStyle);
		StyleRange style1 = new StyleRange();
		style1.start = 0;
		style1.length = cell.getText().length();
		style1.font = bold;
		if (foreground != null) {
			style1.foreground = foreground;
		}
		cell.setStyleRanges(new StyleRange[] { style1 });
	}

	protected boolean isActiveEditor(MPart model) {
		if (model == null || model.getTags() == null) {
			return false;
		}
		return model.getTags().contains(TAG_ACTIVE);
	}

	protected boolean isHiddenEditor(MPart model) {
		if (model == null || model.getParent() == null || !(model.getParent().getRenderer() instanceof StackRenderer)) {
			return false;
		}
		StackRenderer renderer = (StackRenderer) model.getParent().getRenderer();
		CTabItem item = renderer.findItemForPart(model);
		return (item != null && !item.isShowing());
	}

	@Override
	protected void setLabelProvider(TableViewerColumn tableViewerColumn) {

		tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				Object element = cell.getElement();
				if (element instanceof WorkbenchPartReference) {
					WorkbenchPartReference ref = (WorkbenchPartReference) element;
					String text = getWorkbenchPartReferenceText(ref);
					cell.setText(text);
					cell.setImage(ref.getTitleImage());
					MPart model = ref.getModel();
					Color fontForegroundColor = null;
					Integer fontStyle = (isActiveEditor(model)) ? SWT.BOLD : SWT.NORMAL;
					if (isHiddenEditor(model)) {
						fontForegroundColor = colorHidden;
						fontStyle |= SWT.ITALIC;
					}
					setStyle(cell, fontStyle, fontForegroundColor);
				}
			}

			@Override
			public String getToolTipText(Object element) {
				if (element instanceof WorkbenchPartReference) {
					return ((WorkbenchPartReference) element).getTitleToolTip();
				}
				return super.getToolTipText(element);
			}

		});
	}

