				final List<RevTag> tags = getRevTags();
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (!tagViewer.getTable().isDisposed()) {
							tagViewer.setInput(tags);
							tagViewer.getTable().setEnabled(true);
						}
					}
				});
