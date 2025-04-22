	public void fill(Menu menu, int index) {
		if (model == null) {
			return;
		}
		if (widget != null) {
			return;
		}
		int style = SWT.PUSH;
		if (model.getType() == ItemType.PUSH)
			style = SWT.PUSH;
		else if (model.getType() == ItemType.CHECK)
			style = SWT.CHECK;
		else if (model.getType() == ItemType.RADIO)
			style = SWT.RADIO;
		MenuItem item = null;
		if (index >= 0) {
			item = new MenuItem(menu, style, index);
		} else {
			item = new MenuItem(menu, style);
		}
		item.setData(this);

		item.addListener(SWT.Dispose, getItemListener());
		item.addListener(SWT.Selection, getItemListener());
		item.addListener(SWT.DefaultSelection, getItemListener());

		widget = item;
		model.setWidget(widget);
		widget.setData(AbstractPartRenderer.OWNING_ME, model);

		update(null);
	}

	@Override
	public void fill(ToolBar parent, int index) {
		if (model == null) {
			return;
		}
		if (widget != null) {
			return;
		}
		boolean isDropdown = false;
		if (model instanceof MToolItem) {
			MMenu menu = ((MToolItem) model).getMenu();
			isDropdown = menu != null;
		}
		int style = SWT.PUSH;
		if (isDropdown)
			style = SWT.DROP_DOWN;
		else if (model.getType() == ItemType.CHECK)
			style = SWT.CHECK;
		else if (model.getType() == ItemType.RADIO)
			style = SWT.RADIO;
		ToolItem item = null;
		if (index >= 0) {
			item = new ToolItem(parent, style, index);
		} else {
			item = new ToolItem(parent, style);
		}
		item.setData(this);

		item.addListener(SWT.Dispose, getItemListener());
		item.addListener(SWT.Selection, getItemListener());
		item.addListener(SWT.DefaultSelection, getItemListener());

		widget = item;
		model.setWidget(widget);
		widget.setData(AbstractPartRenderer.OWNING_ME, model);

		update(null);
	}

	private void updateVisible() {
		setVisible((model).isVisible());
		final IContributionManager parent = getParent();
		if (parent != null) {
			parent.markDirty();
		}
	}

	@Override
	public void update() {
		update(null);
	}

	@Override
	public void update(String id) {
		updateIcons();
		if (widget instanceof MenuItem) {
			updateMenuItem();
		} else if (widget instanceof ToolItem) {
			updateToolItem();
		}
	}

	private void updateMenuItem() {
