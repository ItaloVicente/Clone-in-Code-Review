					Ref head = repository.getRef(Constants.HEAD);
					if (head == null || head.getObjectId() == null) {
						Shell shell = HandlerUtil.getActiveShell(event);
						MessageDialog.openInformation(shell,
								UIText.CompareWithHeadActionHandler_NoHeadTitle,
								UIText.CompareWithHeadActionHandler_NoHeadMessage);
					} else
						view.setInput(resources, Repository.shortenRefName(head.getTarget().getName()));
