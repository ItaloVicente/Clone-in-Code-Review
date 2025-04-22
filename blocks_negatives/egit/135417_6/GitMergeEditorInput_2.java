				}

				IRunnableContext runnableContext = getContainer();
				if (runnableContext == null)
					runnableContext = PlatformUI.getWorkbench().getProgressService();

				EditableRevision leftEditable;
				if (file != null)
					leftEditable = new ResourceEditableRevision(rev, file,
							runnableContext);
				else
					leftEditable = new LocationEditableRevision(rev, location,
							runnableContext);
				try {
					leftEditable.cacheContents(monitor);
				} catch (CoreException e) {
					throw new IOException(e.getMessage());
