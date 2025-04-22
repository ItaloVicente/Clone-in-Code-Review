
		topWindowHandler = new EventHandler() {

			@Override
			public void handleEvent(Event event) {
				if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MApplication))
					return;
				MWindow win = (MWindow) event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
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
		};

		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT,
				topWindowHandler);

		shellUpdater = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object objElement = event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow))
					return;

				MWindow windowModel = (MWindow) objElement;
				if (windowModel.getRenderer() != WBWRenderer.this)
					return;

				Shell theShell = (Shell) windowModel.getWidget();
				if (theShell == null)
					return;

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);

				if (UIEvents.UILabel.LABEL.equals(attName)
						|| UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
					String newTitle = (String) event
							.getProperty(UIEvents.EventTags.NEW_VALUE);
					theShell.setText(newTitle);
				} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
					theShell.setImage(getImage(windowModel));
				} else if (UIEvents.UILabel.TOOLTIP.equals(attName)
						|| UIEvents.UILabel.LOCALIZED_TOOLTIP.equals(attName)) {
					String newTTip = (String) event
							.getProperty(UIEvents.EventTags.NEW_VALUE);
					theShell.setToolTipText(newTTip);
				}
			}
		};

		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, shellUpdater);

		visibilityHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object objElement = event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(objElement instanceof MWindow))
					return;

				MWindow windowModel = (MWindow) objElement;
				if (windowModel.getRenderer() != WBWRenderer.this)
					return;

				Shell theShell = (Shell) windowModel.getWidget();
				if (theShell == null)
					return;

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);

				if (UIEvents.UIElement.VISIBLE.equals(attName)) {
					boolean isVisible = (Boolean) event
							.getProperty(UIEvents.EventTags.NEW_VALUE);
					theShell.setVisible(isVisible);
				}
			}
		};

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE,
				visibilityHandler);

		sizeHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				if (ignoreSizeChanges)
					return;

				Object objElement = event
						.getProperty(UIEvents.EventTags.ELEMENT);
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

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);

				if (UIEvents.Window.X.equals(attName)
						|| UIEvents.Window.Y.equals(attName)
						|| UIEvents.Window.WIDTH.equals(attName)
						|| UIEvents.Window.HEIGHT.equals(attName)) {
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
		};

		eventBroker.subscribe(UIEvents.Window.TOPIC_ALL, sizeHandler);

