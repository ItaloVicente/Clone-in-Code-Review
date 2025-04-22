					case SQUASH:
						isSquash = true;
					case FIXUP:
						rewindHeadForAChachedCherryPick();
						Step nextStep = (i >= steps.size() ? null
								: steps.get(i + 1));
						File messageFixupFile = rebaseState.getFile(MESSAGE_FIXUP);
						File messageSquashFile = rebaseState
								.getFile(MESSAGE_SQUASH);
						if (isSquash) {
							if (messageFixupFile.exists()) {
								messageFixupFile.delete();
							}
						}
						if (!rebaseState.getFile(DONE).exists()
								|| rebaseState.readFile(DONE).trim().length() == 0) {
							throw new JGitInternalException(
									MessageFormat.format(
											JGitText.get().cannotSquashFixupWithoutPreviousCommit
											step.getAction().name()));
						}
						newHead = doSquashFixup(isSquash
								nextStep
