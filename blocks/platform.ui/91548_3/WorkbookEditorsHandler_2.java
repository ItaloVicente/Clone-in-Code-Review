	@Override
	protected void setLabelProvider(final TableViewerColumn tableViewerColumn) {

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
					StyleRange style = new StyleRange();
					style.start = 0;
					style.length = cell.getText().length();
					if (isActiveEditor(model)) {
						style.font = getBoldFont(cell);
					}
					if (isHiddenEditor(model)) {
						cell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
					}
					cell.setStyleRanges(new StyleRange[] { style });
				}
			}
		});

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

	private Font getBoldFont(ViewerCell cell) {
		if (boldFont == null) {
			FontData data = cell.getFont().getFontData()[0];
			boldFont = new Font(Display.getCurrent(), data.getName(), data.getHeight(), SWT.BOLD);
		}
		return boldFont;
	}

	@Override
	public void dispose() {
		boldFont.dispose();
		super.dispose();
	}

