				if (container instanceof IWorkspaceRoot) {
					Repository repository = RepositoryMapping.getMapping(
							resource.getProject()).getRepository();
					IPath gitIgnorePath = resource.getLocation()
							.removeLastSegments(1)
							.append(Constants.GITIGNORE_FILENAME);
					IPath repoPath = new Path(repository.getWorkTree()
							.getAbsolutePath());
					if (!repoPath.isPrefixOf(gitIgnorePath)) {
						String message = NLS.bind(
								UIText.IgnoreActionHandler_parentOutsideRepo,
								resource.getLocation().toOSString(),
								repoPath.toOSString());
						IStatus status = Activator.createErrorStatus(message,
								null);
						throw new CoreException(status);
					}
					File gitIgnore = new File(gitIgnorePath.toOSString());
					updateGitIgnore(gitIgnore, entry);
					GitLightweightDecorator.refresh();
				} else {
					IFile gitignore = container.getFile(new Path(
							Constants.GITIGNORE_FILENAME));

					if (gitignore.exists())
						gitignore.appendContents(entryBytes, true, true,
								monitor);
					else
						gitignore.create(entryBytes, true, monitor);
				}
			}

			private void updateGitIgnore(File gitIgnore, String entry)
					throws CoreException {
				try {
					if (!gitIgnore.exists())
						if (!gitIgnore.createNewFile()) {
							String error = NLS.bind(
									UIText.IgnoreActionHandler_creatingFailed,
									gitIgnore.getAbsolutePath());
							throw new CoreException(
									Activator.createErrorStatus(error, null));
						}

					FileOutputStream os = new FileOutputStream(gitIgnore, true);
					try {
						os.write(entry.getBytes());
					} finally {
						os.close();
					}
				} catch (IOException e) {
					String error = NLS.bind(
							UIText.IgnoreActionHandler_updatingFailed,
							gitIgnore.getAbsolutePath());
					throw new CoreException(Activator.createErrorStatus(error,
							e));
				}
