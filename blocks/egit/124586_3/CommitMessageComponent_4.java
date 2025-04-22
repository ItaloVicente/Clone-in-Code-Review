				String newCommitter = committerText.getText().trim();
				if (!listenersEnabled || !committerText.isEnabled()) {
					if (!oldCommitter.equals(newCommitter) && RawParseUtils
							.parsePersonIdent(newCommitter) != null) {
						oldCommitter = newCommitter;
					}
