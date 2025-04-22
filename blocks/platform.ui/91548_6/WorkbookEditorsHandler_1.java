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
					style.font = getFont(isActiveEditor(model));
					if (isHiddenEditor(model)) {
						cell.setBackground(getBackgroundColorForHiddenTabs());
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

	private Font getFont(boolean bold) {
		ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
		if (bold) {
			return theme.getFontRegistry().getBold(IWorkbenchThemeConstants.TAB_TEXT_FONT);
		}
		return theme.getFontRegistry().get(IWorkbenchThemeConstants.TAB_TEXT_FONT);
	}

	private Color getBackgroundColorForHiddenTabs() {
		ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
		Color color = theme.getColorRegistry().get(IWorkbenchThemeConstants.INACTIVE_TAB_BG_END);
		if (color != null) {
			return color;
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
	}

