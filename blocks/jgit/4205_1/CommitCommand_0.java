						if (reflogComment != null) {
							ru.setRefLogMessage(reflogComment
						} else {
							String prefix = amend ? "commit (amend): "
									: "commit: ";
							ru.setRefLogMessage(
									prefix + revCommit.getShortMessage()
						}
