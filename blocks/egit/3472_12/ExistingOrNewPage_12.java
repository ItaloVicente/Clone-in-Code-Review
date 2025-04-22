	protected void updateControls() {
		setMessage(null);
		setErrorMessage(null);
		if (!internalMode) {
			setDescription(UIText.ExistingOrNewPage_DescriptionExternalMode);
			if (this.selectedRepository != null) {
				workDir.setText(this.selectedRepository.getWorkTree().getPath());
				String relativePath = relPath.getText();
				File testFile = new File(this.selectedRepository.getWorkTree(),
						relativePath);
				if (!testFile.exists())
					setMessage(
							NLS.bind(
									UIText.ExistingOrNewPage_FolderWillBeCreatedMessage,
									relativePath), IMessageProvider.WARNING);
				IPath targetPath = new Path(selectedRepository.getWorkTree()
						.getPath());
				targetPath = targetPath.append(relPath.getText());
				moveProjectsLabelProvider.targetFolder = targetPath;
				projectMoveViewer.refresh(true);
				browseRepository.setEnabled(this.selectedRepository != null);
				for (Object checked : projectMoveViewer.getCheckedElements()) {
					IProject prj = (IProject) checked;
					IPath projectMoveTarget = targetPath.append(prj.getName());
					boolean mustMove = !prj.getLocation().equals(
							projectMoveTarget);
					File targetTest = new File(projectMoveTarget.toOSString());
					if (mustMove && targetTest.exists()) {
						setErrorMessage(NLS
								.bind(UIText.ExistingOrNewPage_ExistingTargetErrorMessage,
										prj.getName()));
						break;
					}
					File parent = targetTest.getParentFile();
					while (parent != null) {
						if (new File(parent, ".project").exists()) { //$NON-NLS-1$
							setErrorMessage(NLS
									.bind(UIText.ExistingOrNewPage_NestedProjectErrorMessage,
											new String[] { prj.getName(),
													targetTest.getPath(),
													parent.getPath() }));
							break;
						}
						parent = parent.getParentFile();
					}
					if (getErrorMessage() != null)
						break;
				}
			} else
				workDir.setText(UIText.ExistingOrNewPage_NoRepositorySelectedMessage);
			setPageComplete(getErrorMessage() == null
					&& selectedRepository != null
					&& projectMoveViewer.getCheckedElements().length > 0);
		} else {
			setDescription(UIText.ExistingOrNewPage_description);
			minumumPath = null;
			IPath p = null;
			for (TreeItem ti : tree.getSelection()) {
				if (ti.getItemCount() > 0)
					continue;
				String path = ti.getText(2);
				if (!path.equals("")) { //$NON-NLS-1$
					p = null;
					break;
				}
				String gitDirParentCandidate = ti.getText(1);
				IPath thisPath = Path.fromOSString(gitDirParentCandidate);
				if (p == null)
					p = thisPath;
				else {
					int n = p.matchingFirstSegments(thisPath);
					p = p.removeLastSegments(p.segmentCount() - n);
				}
