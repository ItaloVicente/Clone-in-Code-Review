					CherryPickResult cherryPickResult = op.getResult();
					RevCommit newHead = cherryPickResult.getNewHead();
					if (newHead != null
							&& cherryPickResult.getCherryPickedRefs()
									.isEmpty()) {
						showNotPerformedDialog(shell);
					}
					if (newHead == null) {
						CherryPickStatus status = cherryPickResult.getStatus();
						switch (status) {
						case CONFLICTING:
							showConflictDialog(shell);
							break;
						case FAILED:
							showFailure(cherryPickResult);
							break;
						case OK:
							break;
						}
