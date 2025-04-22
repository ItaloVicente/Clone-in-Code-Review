	@Inject
	private EModelService modelService;

	private ISWTResourceUtilities resUtils = null;

	@Inject
	void setResourceUtils(IResourceUtilities<ImageDescriptor> utils) {
		resUtils = (ISWTResourceUtilities) utils;
	}

	@Inject
	@Optional
	private Logger logger;

	private boolean logged = false;

	private ISafeRunnable updateRunner;

	private ISafeRunnable getUpdateRunner() {
		if (updateRunner == null) {
			updateRunner = new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					boolean shouldEnable = canExecuteItem(null);
					if (shouldEnable != model.isEnabled()) {
						model.setEnabled(shouldEnable);
						update();
					}
				}

				@Override
				public void handleException(Throwable exception) {
					if (!logged) {
						logged = true;
						if (logger != null) {
							logger.error(exception,
									"Internal error during tool item enablement updating, this is only logged once per tool item."); //$NON-NLS-1$
						}
					}
				}
			};
		}
		return updateRunner;
	}

	private IMenuListener menuListener = new IMenuListener() {
		@Override
		public void menuAboutToShow(IMenuManager manager) {
			update(null);
		}
	};

	public void setModel(MItem item) {
		model = item;
		setId(model.getElementId());
		updateVisible();
	}

	@Override
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

		ToolItemUpdater updater = getUpdater();
		if (updater != null) {
			updater.registerItem(this);
		}

		update(null);
	}

	private void updateVisible() {
		setVisible((model).isVisible());
		final IContributionManager parent = getParent();
		if (parent != null) {
			parent.markDirty();
		}
	}

