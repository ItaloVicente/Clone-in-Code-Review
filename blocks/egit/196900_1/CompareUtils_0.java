	public static void compareFiles(IFile aFile, IFile bFile, File a, File b,
			IWorkbenchPart sourcePart) {
		List<IFile> toDelete = new ArrayList<>();
		IFile aTmp = aFile;
		IFile bTmp = bFile;
		try {
			if (aTmp == null) {
				aTmp = HiddenResources.INSTANCE.createFile(a.toURI(),
						a.getName(), null, null);
				toDelete.add(aTmp);
			}
			if (bTmp == null) {
				bTmp = HiddenResources.INSTANCE.createFile(b.toURI(),
						b.getName(), null, null);
				toDelete.add(bTmp);
			}
		} catch (CoreException e) {
			Activator.logError(UIText.CompareUtils_errorHiddenResourceCreate,
					e);
			cleanHiddenResources(toDelete);
			return;
		}
		@SuppressWarnings("restriction")
		class CompareWithEachOther extends CompareAction {

			@Override
			public void run(ISelection selection) {
				if (isEnabled(selection)) {
					if (!toDelete.isEmpty()) {
						CompareEditorInput input = fInput;
						fWorkbenchPage.addPartListener(new IPartListener() {

							private final IWorkbenchPage page = fWorkbenchPage;

							private IWorkbenchPart compareEditor;

							@Override
							public void partActivated(IWorkbenchPart part) {
								if ((part instanceof EditorPart)
										&& ((EditorPart) part)
												.getEditorInput() == input) {
									compareEditor = part;
								}
							}

							@Override
							public void partBroughtToTop(IWorkbenchPart part) {
							}

							@Override
							public void partClosed(IWorkbenchPart part) {
								if (part != null && part == compareEditor) {
									cleanHiddenResources(toDelete);
									page.removePartListener(this);
								}
							}

							@Override
							public void partDeactivated(IWorkbenchPart part) {
							}

							@Override
							public void partOpened(IWorkbenchPart part) {
							}

						});
					}
					super.run(selection);
				} else {
					cleanHiddenResources(toDelete);
				}
			}

			@Override
			public void setActivePart(IAction action,
					IWorkbenchPart targetPart) {
				super.setActivePart(action, targetPart);
			}
		}
		CompareWithEachOther compare = new CompareWithEachOther();
		compare.setActivePart(null, sourcePart);
		IStructuredSelection selection = new StructuredSelection(
				List.of(aTmp, bTmp));
		compare.run(selection);
	}

	public static void cleanHiddenResources(Collection<IFile> toClean) {
		if (toClean == null || toClean.isEmpty()) {
			return;
		}
		Job job = new Job(UIText.CompareUtils_ResourceCleanupJobName) {

			@Override
			public boolean shouldSchedule() {
				return super.shouldSchedule()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			public boolean shouldRun() {
				return super.shouldRun()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspaceRunnable remove = m -> {
					SubMonitor progress = SubMonitor.convert(m, toClean.size());
					for (IFile tmp : toClean) {
						if (PlatformUI.getWorkbench().isClosing()) {
							return;
						}
						try {
							tmp.delete(true, progress.newChild(1));
						} catch (CoreException e) {
						}
					}
				};
				try {
					ResourcesPlugin.getWorkspace().run(remove, null,
							IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

