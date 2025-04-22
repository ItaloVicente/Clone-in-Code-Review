
	@Override
	public void reset() {
		super.reset();
		TableItem tableItem = getTableItem();
		tableItem.setForeground(null);
		tableItem.setBackground(null);
		tableItem.setImage((Image) null);
		tableItem.setFont(null); // in such case the parent's font will be taken

		Table parent = tableItem.getParent();
		parent.setForeground(null);
		parent.setBackground(null);
		parent.setFont(parent.getDisplay().getSystemFont());
	}
