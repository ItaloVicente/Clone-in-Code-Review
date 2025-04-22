	/**
	 * Create the button that clears the text.
	 *
	 * @param parent
	 *            parent <code>Composite</code> of toolbar button
	 */
	private void createClearText(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {

			final Label clearButton = new Label(parent, SWT.NONE);
			ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), clearButton);

			final Image inactiveImage = resourceManager
					.createImage(JFaceResources.getImageRegistry().getDescriptor(DISABLED_CLEAR_ICON));
			final Image activeImage = resourceManager
					.createImage(JFaceResources.getImageRegistry().getDescriptor(CLEAR_ICON));
			final Image pressedImage = resourceManager
					.createImage(JFaceResources.getImageRegistry().getDescriptor(PRESSED_CLEAR_ICON));

			clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
			clearButton.setImage(inactiveImage);
			clearButton.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			clearButton.setToolTipText(E4DialogMessages.FilteredTree_ClearToolTip);
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
			clearButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = E4DialogMessages.FilteredTree_AccessibleListenerClearButton;
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
	}

