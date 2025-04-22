	public void setParent(IContributionManager parent) {
		if (getParent() instanceof IMenuManager) {
			IMenuManager menuMgr = (IMenuManager) getParent();
			menuMgr.removeMenuListener(menuListener);
		}
		if (parent instanceof IMenuManager) {
			IMenuManager menuMgr = (IMenuManager) parent;
			menuMgr.addMenuListener(menuListener);
		}
		super.setParent(parent);
	}

	/**
	 * Return a parent context for this part.
	 *
	 * @param element
	 *            the part to start searching from
	 * @return the parent's closest context, or global context if none in the
	 *         hierarchy
	 */
	protected IEclipseContext getContextForParent(MUIElement element) {
		return modelService.getContainingContext(element);
	}

	/**
	 * Return a context for this part.
	 *
	 * @param part
	 *            the part to start searching from
	 * @return the closest context, or global context if none in the hierarchy
	 */
	protected IEclipseContext getContext(MUIElement part) {
		if (part instanceof MContext) {
			return ((MContext) part).getContext();
		}
		return getContextForParent(part);
	}

	public Widget getWidget() {
		return widget;
	}

	/**
	 * @return the model
	 */
	public MItem getModel() {
		return model;
	}

	private ToolItemUpdater getUpdater() {
		if (model != null) {
			Object obj = model.getRenderer();
			if (obj instanceof ToolBarManagerRenderer) {
				return ((ToolBarManagerRenderer) obj).getUpdater();
			}
		}
		return null;
	}

	protected void updateItemEnablement() {
		if (!(model.getWidget() instanceof ToolItem))
			return;

		ToolItem widget = (ToolItem) model.getWidget();
		if (widget == null || widget.isDisposed())
			return;

		SafeRunner.run(getUpdateRunner());
