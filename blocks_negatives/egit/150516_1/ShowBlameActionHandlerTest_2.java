
	private static boolean setAutobuild(boolean value) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = workspace.getDescription();
		boolean isAutoBuilding = desc.isAutoBuilding();
		if (isAutoBuilding != value) {
			desc.setAutoBuilding(value);
			workspace.setDescription(desc);
		}
		return isAutoBuilding;
	}

	private IJavaProject createJavaProjectAndCommitToRepository()
			throws Exception {
		Repository myRepository = createLocalTestRepository(REPO1);
		File gitDir = myRepository.getDirectory();
		IJavaProject jProject = createJavaProject(myRepository,
				JAVA_PROJECT_NAME);
		IProject project = jProject.getProject();
		try {
			new ConnectProviderOperation(project, gitDir).execute(null);
		} catch (Exception e) {
			Activator.logError("Failed to connect project to repository", e);
		}
		assertConnected(project);
		IFolder folder = project.getFolder(SRC_FOLDER_NAME)
				.getFolder(PACKAGE_NAME);
		IFile file = folder.getFile(JAVA_FILE_NAME);

		IFile[] commitables = new IFile[] { file };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables, untracked,
				TestUtil.TESTAUTHOR, TestUtil.TESTCOMMITTER, "Initial commit");
		op.execute(null);

		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		cache.getIndexDiffCacheEntry(lookupRepository(gitDir));
		return jProject;
	}

	private IJavaProject createJavaProject(final Repository repository,
			final String projectName) throws Exception {
		final IJavaProject[] jProjectHolder = new IJavaProject[] { null };
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject(projectName);
				if (project.exists()) {
					project.delete(true, null);
					TestUtil.waitForJobs(100, 5000);
				}
				IProjectDescription desc = ResourcesPlugin.getWorkspace()
						.newProjectDescription(projectName);
				desc.setLocation(
						new Path(new File(repository.getWorkTree(), projectName)
								.getPath()));
				project.create(desc, null);
				project.open(null);
				TestUtil.waitForJobs(50, 5000);
				IFolder bin = project.getFolder(BIN_FOLDER_NAME);
				if (!bin.exists()) {
					bin.create(IResource.FORCE | IResource.DERIVED, true, null);
				}
				IPath outputLocation = bin.getFullPath();
				IFolder src = project.getFolder(SRC_FOLDER_NAME);
				if (!src.exists()) {
					src.create(IResource.FORCE, true, null);
				}
				addNatureToProject(project, JavaCore.NATURE_ID);
				IJavaProject jProject = JavaCore.create(project);
				IPackageFragmentRoot srcContainer = jProject
						.getPackageFragmentRoot(src);
				IClasspathEntry srcEntry = JavaCore
						.newSourceEntry(srcContainer.getPath());
				IClasspathEntry jreEntry = JavaRuntime
						.getDefaultJREContainerEntry();
				jProject.setRawClasspath(
						new IClasspathEntry[] { srcEntry, jreEntry },
						outputLocation, true, null);
				IPackageFragment javaPackage = srcContainer
						.createPackageFragment(PACKAGE_NAME, true, null);
				javaPackage
						.createCompilationUnit(JAVA_FILE_NAME,
								"package " + PACKAGE_NAME + ";\nclass "
										+ JAVA_CLASS_NAME + " {\n\n}",
								true, null);
				jProjectHolder[0] = jProject;
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
		return jProjectHolder[0];
	}

	private void addNatureToProject(IProject proj, String natureId)
			throws CoreException {
		IProjectDescription description = proj.getDescription();
		String[] prevNatures = description.getNatureIds();
		String[] newNatures = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = natureId;
		description.setNatureIds(newNatures);
		proj.setDescription(description, null);
	}

	private void removeJavaProject() throws CoreException {
		if (javaProject == null) {
			return;
		}
		final IProject project = javaProject.getProject();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				for (int i = 0; i < MAX_DELETE_RETRY; i++) {
					try {
						project.delete(
								IResource.FORCE
										| IResource.ALWAYS_DELETE_PROJECT_CONTENT,
								null);
						break;
					} catch (CoreException e) {
						if (i == MAX_DELETE_RETRY - 1) {
							throw e;
						}
						try {
							Activator.logInfo(
									"Sleep before retrying to delete project "
											+ project.getLocationURI());
							Thread.sleep(DELETE_RETRY_DELAY);
						} catch (InterruptedException e1) {
						}
					}
				}

			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
