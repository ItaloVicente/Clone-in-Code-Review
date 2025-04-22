		if (filterComposite != null && (!useNewLook || useNativeSearchField(filterComposite))) {
			filterComposite.setBackground(background);
		}
		if (filterToolBar != null && filterToolBar.getControl() != null) {
			filterToolBar.getControl().setBackground(background);
		}
	}

	/**
	 * Create the button that clears the text.
	 *
	 * @param parent parent <code>Composite</code> of toolbar button
	 */
	private void createClearTextOld(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
			filterToolBar.createControl(parent);

			IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
				@Override
				public void run() {
					clearText();
				}
			};

			clearTextAction.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
			clearTextAction.setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(CLEAR_ICON));
			clearTextAction
					.setDisabledImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(DISABLED_CLEAR_ICON));

			filterToolBar.add(clearTextAction);
		}
	}

	/**
	 * Create the button that clears the text.
	 *
	 * @param parent parent <code>Composite</code> of toolbar button
	 */
	private void createClearTextNew(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			final Image inactiveImage = JFaceResources.getImageRegistry().getDescriptor(DISABLED_CLEAR_ICON)
					.createImage();
			final Image activeImage = JFaceResources.getImageRegistry().getDescriptor(CLEAR_ICON).createImage();
			final Image pressedImage = new Image(getDisplay(), activeImage, SWT.IMAGE_GRAY);

			final Label clearButton = new Label(parent, SWT.NONE);
			clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
			clearButton.setImage(inactiveImage);
			clearButton.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			clearButton.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
			clearButton.addMouseListener(new MouseAdapter() {
				private MouseMoveListener fMoveListener;

				@Override
				public void mouseDown(MouseEvent e) {
					clearButton.setImage(pressedImage);
					fMoveListener = new MouseMoveListener() {
						private boolean fMouseInButton = true;

						@Override
						public void mouseMove(MouseEvent e) {
							boolean mouseInButton = isMouseInButton(e);
							if (mouseInButton != fMouseInButton) {
								fMouseInButton = mouseInButton;
								clearButton.setImage(mouseInButton ? pressedImage : inactiveImage);
							}
						}
					};
					clearButton.addMouseMoveListener(fMoveListener);
				}

				@Override
				public void mouseUp(MouseEvent e) {
					if (fMoveListener != null) {
						clearButton.removeMouseMoveListener(fMoveListener);
						fMoveListener = null;
						boolean mouseInButton = isMouseInButton(e);
						clearButton.setImage(mouseInButton ? activeImage : inactiveImage);
						if (mouseInButton) {
							clearText();
							filterText.setFocus();
						}
					}
				}

				private boolean isMouseInButton(MouseEvent e) {
					Point buttonSize = clearButton.getSize();
					return 0 <= e.x && e.x < buttonSize.x && 0 <= e.y && e.y < buttonSize.y;
				}
			});
			clearButton.addMouseTrackListener(new MouseTrackListener() {
				@Override
				public void mouseEnter(MouseEvent e) {
					clearButton.setImage(activeImage);
				}

				@Override
				public void mouseExit(MouseEvent e) {
					clearButton.setImage(inactiveImage);
				}

				@Override
				public void mouseHover(MouseEvent e) {
				}
			});
			clearButton.addDisposeListener(e -> {
				inactiveImage.dispose();
				activeImage.dispose();
				pressedImage.dispose();
			});
			clearButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = WorkbenchMessages.FilteredTree_AccessibleListenerClearButton;
				}
			});
			clearButton.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
				@Override
				public void getRole(AccessibleControlEvent e) {
					e.detail = ACC.ROLE_PUSHBUTTON;
				}
			});
			this.clearButtonControl = clearButton;
		}
