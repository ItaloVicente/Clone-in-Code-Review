					clearButton.setImage(pressedImage);
					fMoveListener = new MouseMoveListener() {
						private boolean fMouseInButton = true;

						@Override
						public void mouseMove(MouseEvent e) {
							boolean mouseInButton = isMouseInButton(e);
							if (mouseInButton != fMouseInButton) {
								fMouseInButton = mouseInButton;
								clearButton
								.setImage(mouseInButton ? pressedImage
										: inactiveImage);
							}
						}
					};
					clearButton.addMouseMoveListener(fMoveListener);
