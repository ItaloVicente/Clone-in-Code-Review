					CherryPickResult cherryPickResult = op.getResult();
					RevCommit newHead = cherryPickResult.getNewHead();
					if (newHead != null
							&& cherryPickResult.getCherryPickedRefs().isEmpty())
						showNotPerformedDialog(parent);
					if (newHead == null) {
						CherryPickStatus status = cherryPickResult.getStatus();
						switch (status) {
						case CONFLICTING:
							showConflictDialog(parent);
							break;
						case FAILED:
							showFailure(cherryPickResult);
							break;
						case OK:
							break;
						}
					}
