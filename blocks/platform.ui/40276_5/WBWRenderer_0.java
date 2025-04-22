	@Inject
	@Optional
	private void subscribeTopicSelectedElementChanged(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MApplication))
			return;
		MWindow win = (MWindow) event.getProperty(UIEvents.EventTags.NEW_VALUE);
		if ((win == null) || !win.getTags().contains("topLevel")) //$NON-NLS-1$
			return;
		win.setToBeRendered(true);
		if (!(win.getRenderer() == WBWRenderer.this))
			return;
		Shell shell = (Shell) win.getWidget();
		if (shell.getMinimized()) {
			shell.setMinimized(false);
		}
		shell.setActive();
		shell.moveAbove(null);
	}

	@Inject
	@Optional
	private void subscribeTopicLabelChanged(@UIEventTopic(UIEvents.UILabel.TOPIC_ALL) Event event) {
		Object objElement = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow))
			return;

		MWindow windowModel = (MWindow) objElement;
		if (windowModel.getRenderer() != WBWRenderer.this)
			return;

		Shell theShell = (Shell) windowModel.getWidget();
		if (theShell == null)
			return;

		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);

		if (UIEvents.UILabel.LABEL.equals(attName) || UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
			String newTitle = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			theShell.setText(newTitle);
		} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
			theShell.setImage(getImage(windowModel));
		} else if (UIEvents.UILabel.TOOLTIP.equals(attName) || UIEvents.UILabel.LOCALIZED_TOOLTIP.equals(attName)) {
			String newTTip = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			theShell.setToolTipText(newTTip);
		}
	}

	@Inject
	@Optional
	private void subscribeTopicWindowChanged(@UIEventTopic(UIEvents.Window.TOPIC_ALL) Event event) {
		if (ignoreSizeChanges)
			return;

		Object objElement = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(objElement instanceof MWindow)) {
			return;
		}

		MWindow windowModel = (MWindow) objElement;
		if (windowModel.getRenderer() != WBWRenderer.this) {
			return;
		}

		Shell theShell = (Shell) windowModel.getWidget();
		if (theShell == null) {
			return;
		}

		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);

		if (UIEvents.Window.X.equals(attName) || UIEvents.Window.Y.equals(attName)
				|| UIEvents.Window.WIDTH.equals(attName) || UIEvents.Window.HEIGHT.equals(attName)) {
			if (boundsJob == null) {
				boundsJob = new WindowSizeUpdateJob();
				boundsJob.windowsToUpdate.add(windowModel);
				theShell.getDisplay().asyncExec(boundsJob);
			} else {
				if (!boundsJob.windowsToUpdate.contains(windowModel))
					boundsJob.windowsToUpdate.add(windowModel);
			}
		}
	}

	@Inject
	@Optional
	private void subscribeTopicVisibleChanged(@UIEventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {
		Object objElement = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(objElement instanceof MWindow))
			return;

		MWindow windowModel = (MWindow) objElement;
		if (windowModel.getRenderer() != WBWRenderer.this)
			return;

		Shell theShell = (Shell) windowModel.getWidget();
		if (theShell == null)
			return;

		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);

		if (UIEvents.UIElement.VISIBLE.equals(attName)) {
			boolean isVisible = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			theShell.setVisible(isVisible);
		}
	}

	@Inject
	@Optional
	private void subscribeThemeDefinitionChanged(
			@UIEventTopic(UIEvents.UILifeCycle.THEME_DEFINITION_CHANGED) Event event) {
		themeDefinitionChanged.handleEvent(event);
	}

