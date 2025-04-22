	public boolean isShowFilterControls() {
		return showFilterControls;
	}

	public void setShowFilterControls(boolean showFilterControls) {
		this.showFilterControls = showFilterControls;
		if (filterComposite != null) {
			Object filterCompositeLayoutData = filterComposite.getLayoutData();
			if (filterCompositeLayoutData instanceof GridData) {
				((GridData) filterCompositeLayoutData).exclude = !isShowFilterControls();
			} else if (filterCompositeLayoutData instanceof RowData) {
				((RowData) filterCompositeLayoutData).exclude = !isShowFilterControls();
			}
			filterComposite.setVisible(isShowFilterControls());
			layout();
		}
	}

