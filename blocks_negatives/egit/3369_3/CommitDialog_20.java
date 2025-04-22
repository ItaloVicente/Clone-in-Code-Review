					List<IResource> filesToAdd = new ArrayList<IResource>();
					for (Iterator<?> it = sel.iterator(); it.hasNext();) {
						CommitItem commitItem = (CommitItem) it.next();
						filesToAdd.add(commitItem.file);
					}
					AddToIndexOperation op = new AddToIndexOperation(filesToAdd);
					op.execute(new NullProgressMonitor());
					for (Iterator<?> it = sel.iterator(); it.hasNext();) {
						CommitItem commitItem = (CommitItem) it.next();
						commitItem.status = getFileStatus(commitItem.file);
					}
					filesViewer.refresh(true);
				} catch (CoreException e) {
					Activator.logError(UIText.CommitDialog_ErrorAddingFiles, e);
					return;
				} catch (IOException e) {
