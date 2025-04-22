	protected void buttonPressed(int buttonId) {
		if (toggleButton != null)
			Activator.getDefault().getPreferenceStore().setValue(
					UIPreferences.REBASE_HIDE_CONFIRM,
					toggleButton.getSelection());
		if (buttonId == IDialogConstants.OK_ID) {
			if (result.getStatus() != Status.STOPPED) {
				super.buttonPressed(buttonId);
				return;
			}
			if (startMergeButton.getSelection()) {
				super.buttonPressed(buttonId);
				List<IProject> validProjects = new ArrayList<IProject>();
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				for (IProject project : projects) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(project);
					if (mapping != null && mapping.getRepository().equals(repo)) {
						validProjects.add(project);
						try {
							project
									.refreshLocal(IResource.DEPTH_INFINITE,
											null);
						} catch (CoreException e) {
							Activator.handleError(e.getMessage(), e, false);
						}
					}
				}
				List<IResource> resourceList = new ArrayList<IResource>();
				IPath repoWorkdirPath = new Path(repo.getWorkTree().getPath());
				for (String repoPath : conflictPaths) {
					IPath filePath = repoWorkdirPath.append(repoPath);
					for (IProject project : validProjects) {
						if (project.getLocation().isPrefixOf(filePath)) {
							IResource res = project.getFile(filePath
									.removeFirstSegments(project.getLocation()
											.segmentCount()));
							resourceList.add(res);
						}
					}
				}
				IResource[] resources = new IResource[resourceList.size()];
				resourceList.toArray(resources);
				int mergeMode = Activator.getDefault().getPreferenceStore()
						.getInt(UIPreferences.MERGE_MODE);
				CompareEditorInput input;
				if (mergeMode == 0) {
					MergeModeDialog dlg = new MergeModeDialog(getParentShell());
					if (dlg.open() != Window.OK)
						return;
					input = new GitMergeEditorInput(dlg.useWorkspace(),
							resources);
				} else {
					boolean useWorkspace = mergeMode == 1;
					input = new GitMergeEditorInput(useWorkspace, resources);
				}
				CompareUI.openCompareEditor(input);
				return;
			} else if (abortRebaseButton.getSelection()) {
				if (result.getStatus() == Status.STOPPED) {
					try {
						final RebaseOperation op = new RebaseOperation(repo,
								Operation.ABORT);
						op.execute(null);

						show(op.getResult(), repo);
					} catch (CoreException e) {
						Activator.handleError(e.getMessage(), e, true);
					}
				}
			} else if (doNothingButton.getSelection()) {
			}
		}
		super.buttonPressed(buttonId);
	}

	private void createToggleButton(Composite parent) {
		boolean toggleState = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.REBASE_HIDE_CONFIRM);
		toggleButton = new Button(parent, SWT.CHECK | SWT.LEFT);
		toggleButton.setText(UIText.RebaseResultDialog_ToggleShowButton);
		toggleButton.setSelection(toggleState);
