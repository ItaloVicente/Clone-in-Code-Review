	@Inject
	@Optional
	private void subscribeTopicTagsChanged(
			@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {

		Object changedObj = event.getProperty(EventTags.ELEMENT);

		if (!(changedObj instanceof MToolControl))
			return;

		final MUIElement changedElement = (MUIElement) changedObj;

		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					ToolBarManagerRenderer.HIDDEN_BY_USER)) {
				changedElement.setVisible(false);
				changedElement.setToBeRendered(false);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					ToolBarManagerRenderer.HIDDEN_BY_USER)) {
				changedElement.setVisible(true);
				changedElement.setToBeRendered(true);
			}
		}
	}

	@Inject
	@Optional
	private void subscribeTopicAppStartup(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
		List<MToolControl> toolControls = modelService.findElements(
				application, null, MToolControl.class, null);
		for (MToolControl toolControl : toolControls) {
			if (toolControl.getTags().contains(
					ToolBarManagerRenderer.HIDDEN_BY_USER)) {
				toolControl.setVisible(false);
				toolControl.setToBeRendered(false);
			}
		}
	}

	private void createToolControlMenu(final MToolControl toolControl,
			Control renderedCtrl) {
		toolControlMenu = new Menu(renderedCtrl);
		MenuItem hideItem = new MenuItem(toolControlMenu, SWT.NONE);
		hideItem.setText(Messages.ToolBarManagerRenderer_MenuCloseText);
		hideItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				toolControl.getTags()
						.add(ToolBarManagerRenderer.HIDDEN_BY_USER);
			}
		});

		new MenuItem(toolControlMenu, SWT.SEPARATOR);

		MenuItem restoreHiddenItems = new MenuItem(toolControlMenu, SWT.NONE);
		restoreHiddenItems
				.setText(Messages.ToolBarManagerRenderer_MenuRestoreText);
		restoreHiddenItems.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				removeHiddenByUserTags(toolControl);
			}
		});
		renderedCtrl.setMenu(toolControlMenu);

	}

	private void removeHiddenByUserTags(MToolControl toolControl) {
		MWindow mWindow = modelService.getTopLevelWindowFor(toolControl);
		List<MToolControl> findElements = modelService.findElements(mWindow,
				null, MToolControl.class, null);
		for (MToolControl mToolControl : findElements) {
			mToolControl.getTags()
					.remove(ToolBarManagerRenderer.HIDDEN_BY_USER);
		}
	}
