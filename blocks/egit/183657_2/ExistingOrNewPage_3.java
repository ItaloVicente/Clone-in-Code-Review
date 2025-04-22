				if (getSeparateMode()) {
					if (relPath.isEnabled()) {
						relPath.setEnabled(false);
						relPath.setText(""); //$NON-NLS-1$
					}
					browseRepository.setEnabled(false);
					if (projectMoveViewer.getSelection().isEmpty()) {
						projectMoveViewer.getTable().setSelection(0);
					}
					IStructuredSelection sel = (IStructuredSelection) projectMoveViewer
							.getSelection();
					IProject prj = (IProject) sel.getFirstElement();
					IWorkspaceRoot workspaceRoot = ResourcesPlugin
							.getWorkspace().getRoot();
					if (workspaceRoot.getLocation()
							.isPrefixOf(prj.getLocation())) {
						workDir.setText(prj.getLocation().toOSString());
					} else {
						IPath proposedPath = workspaceRoot.getLocation()
								.append(new Path(prj.getName()));
						File proposedFile = proposedPath.toFile();
						if (!proposedFile.exists()) {
							workDir.setText(proposedPath.toOSString());
						}
					}
					workDir.setEnabled(true);
				} else {
					relPath.setEnabled(true);
					browseRepository.setEnabled(true);
					workDir.setEnabled(false);
					workDir.setText(
							this.selectedRepository.getWorkTree().getPath());
				}

