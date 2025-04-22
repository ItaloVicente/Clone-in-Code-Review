				for (Iterator<?> it = sel.iterator(); it.hasNext();) {
					CommitItem commitItem = (CommitItem) it.next();
					try {
						commitItem.status = getFileStatus(commitItem.path);
					} catch (IOException e) {
						Activator.logError(UIText.CommitDialog_ErrorAddingFiles, e);
					}
				}
				filesViewer.refresh(true);
