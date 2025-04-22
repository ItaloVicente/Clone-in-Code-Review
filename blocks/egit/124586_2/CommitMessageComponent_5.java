				}
				if (!oldCommitter.equals(newCommitter) && RawParseUtils
						.parsePersonIdent(newCommitter) != null) {
					String oldCommitText = commitText.getText();
					String newCommitText = oldCommitText;
					String currentAuthor = authorText.getText().trim();
					if (newCommitter.equals(currentAuthor)) {
						if (signedOff) {
							String signOff = getSignedOff(newCommitter);
							if (!hasSignOff(oldCommitText, signOff)) {
								newCommitText = signOff(oldCommitText);
							}
						}
					} else {
						String oldSignOff = getSignedOff(oldCommitter);
						String newSignOff = getSignedOff(newCommitter);
						newCommitText = replaceSignOff(oldCommitText,
								oldSignOff, newSignOff);
					}
					if (!oldCommitText.equals(newCommitText)) {
						commitText.setText(newCommitText);
					}
