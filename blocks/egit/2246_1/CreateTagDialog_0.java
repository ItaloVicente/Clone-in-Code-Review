
				try {
					final List<Ref> tags = getRevTags();
					PlatformUI.getWorkbench().getDisplay().asyncExec(
							new Runnable() {
								public void run() {
									if (!tagViewer.getTable().isDisposed()) {
										tagViewer.setInput(tags);
										tagViewer.getTable().setEnabled(true);
									}
								}
							});
				} catch (IOException e) {
					setErrorMessage(UIText.CreateTagDialog_ExceptionRetrievingTagsMessage);
					return Activator.createErrorStatus(e.getMessage(), e);
				}
