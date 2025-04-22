	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		initAdditionalActions();
	}

	private void initAdditionalActions() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager tm = bars.getToolBarManager();
		for (int i = 0; i < 11; i++) {
			if (i % 2 == 0) {
				tm.add(new Separator());// "group i")); //$NON-NLS-1$
			}
			tm.add(new Action("action " + i, //$NON-NLS-1$
					PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT)) {
			});
		}
	}

