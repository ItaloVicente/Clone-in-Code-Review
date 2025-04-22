					if (fMoveListener != null) {
						clearButton.removeMouseMoveListener(fMoveListener);
						fMoveListener = null;
						boolean mouseInButton = isMouseInButton(e);
						clearButton.setImage(mouseInButton ? activeImage
								: inactiveImage);
						if (mouseInButton) {
							clearText();
							filterText.setFocus();
						}
