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
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				changedElement.setVisible(false);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				changedElement.setVisible(true);
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
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				toolControl.setVisible(false);
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
						.add(IPresentationEngine.HIDDEN_EXPLICITLY);
			}
		});

		new MenuItem(toolControlMenu, SWT.SEPARATOR);

		MenuItem restoreHiddenItems = new MenuItem(toolControlMenu, SWT.NONE);
		restoreHiddenItems
				.setText(Messages.ToolBarManagerRenderer_MenuRestoreText);
		restoreHiddenItems.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				removeHiddenTags(toolControl);
			}
		});
		renderedCtrl.setMenu(toolControlMenu);

	}

	/**
	 * Removes the IPresentationEngine.HIDDEN_EXPLICITLY from the trimbar
	 * entries. Having a separate logic for toolbars and toolcontrols would be
	 * confusing for the user, hence we remove this tag for both these types
	 *
	 * @param toolbarModel
	 */
	private void removeHiddenTags(MToolControl toolControl) {
		MWindow mWindow = modelService.getTopLevelWindowFor(toolControl);
		List<MTrimElement> trimElements = modelService.findElements(mWindow,
				null, MTrimElement.class, null);
		for (MTrimElement trimElement : trimElements) {
			trimElement.getTags().remove(IPresentationEngine.HIDDEN_EXPLICITLY);
		}
	}

