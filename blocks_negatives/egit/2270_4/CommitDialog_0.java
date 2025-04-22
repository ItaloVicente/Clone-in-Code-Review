						String curText = commitText.getText();
						if (curText.length() > 0)
							curText += Text.DELIMITER;
						commitText.setText(curText
								+ previousCommitMessage.replaceAll(
										"\n", Text.DELIMITER)); //$NON-NLS-1$
