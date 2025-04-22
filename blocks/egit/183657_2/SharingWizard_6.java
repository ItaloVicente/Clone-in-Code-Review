			if (existingPage.getSeparateMode()) {
				try {
					Entry<IProject, File> entry = existingPage.getProjects(true)
							.entrySet().iterator().next();
					IProject project = entry.getKey();
					File workingDir = project.getLocation()
							.toFile();
					final Repository selectedRepository = existingPage
							.getSelectedRepository();
					File gitDir = selectedRepository.getDirectory();
					final Repository reinitializedRepo = Git.init()
							.setDirectory(workingDir).setGitDir(gitDir).call()
							.getRepository();
					reinitializedRepo.getConfig().setString(
							ConfigConstants.CONFIG_CORE_SECTION, null,
							ConfigConstants.CONFIG_KEY_WORKTREE,
							workingDir.getAbsolutePath());
					reinitializedRepo.getConfig().save();
					IFile agnosticSymLink = project.getFile(Constants.DOT_GIT);
					if (!agnosticSymLink.exists()) {
						String link = Constants.GITDIR
								+ gitDir.getAbsolutePath();
						InputStream source = new ByteArrayInputStream(
								link.getBytes());
						agnosticSymLink.create(source, IResource.NONE, null);
					}
					RepositoryCache.INSTANCE.removeRepository(gitDir);
					RepositoryCache.INSTANCE
							.lookupRepository(reinitializedRepo.getDirectory());
					getContainer().run(true, false,
							new IRunnableWithProgress() {
								@Override
								public void run(IProgressMonitor monitor)
										throws InvocationTargetException {
									try {
										new ConnectProviderOperation(project,
												gitDir).execute(monitor);
									} catch (CoreException e) {
										throw new InvocationTargetException(e);
									}
								}
							});
				} catch (IllegalStateException | GitAPIException
						| IOException | InvocationTargetException
						| InterruptedException | CoreException e) {
					Activator.handleError(UIText.SharingWizard_failed,
							e.getCause(), true);
					return false;
				}
				return true;
			}
