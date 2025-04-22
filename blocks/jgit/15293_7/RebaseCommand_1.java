					case SQUASH:
						isSquash = true;
					case FIXUP:
						resetSoftToParent();
						RebaseTodoLine nextStep = (i >= steps.size() - 1 ? null
								: steps.get(i + 1));
						File messageFixupFile = rebaseState.getFile(MESSAGE_FIXUP);
						File messageSquashFile = rebaseState
								.getFile(MESSAGE_SQUASH);
						if (isSquash) {
							if (messageFixupFile.exists()) {
								messageFixupFile.delete();
							}
						}
						newHead = doSquashFixup(isSquash
								nextStep
