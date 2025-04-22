				if (isChecked())
					saveCommitMessageComponentState();
				else {
					CommitMessageComponentState oldState = loadCommitMessageComponentState();
					if (oldState != null) {
						commitMessageComponent.setCommitMessage(oldState.getCommitMessage());
						commitMessageComponent.updateUIFromState();
					}
				}
