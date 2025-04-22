	protected void updateControls() {
		setMessage(null);
		setErrorMessage(null);
		if (externalMode) {
			if (this.selectedRepository != null) {
				workDir.setText(this.selectedRepository.getWorkTree().getPath());
				String relativePath = relPath.getText();
				File testFile = new File(this.selectedRepository.getWorkTree(),
						relativePath);
				if (!testFile.exists())
					setMessage(
							NLS.bind(
									"Folder {0} does not exist in working directory, will be created", relativePath), IMessageProvider.WARNING); //$NON-NLS-1$
				IPath targetPath = new Path(selectedRepository.getWorkTree()
						.getPath());
				targetPath = targetPath.append(relPath.getText());
				moveProjectsLabelProvider.targetFolder = targetPath;
				projectMoveViewer.refresh(true);
				browseRepository.setEnabled(this.selectedRepository != null);
				for (Object checked : projectMoveViewer.getCheckedElements()) {
					IProject prj = (IProject) checked;
					IPath projectMoveTarget = targetPath.append(prj.getName());
					File targetTest = new File(projectMoveTarget.toOSString());
					if (targetTest.exists()) {
						setErrorMessage(NLS
								.bind("Target location for project {0} already exists, can not move project", prj.getName())); //$NON-NLS-1$
						setPageComplete(false);
						return;
					}
					File parent = targetTest.getParentFile();
					while (parent != null) {
						if (new File(parent, ".project").exists()) { //$NON-NLS-1$
							setErrorMessage(NLS
									.bind("Paraent folder {0} of target location for project {1} already contains a .project file, can not move project", parent.getPath(), prj.getName())); //$NON-NLS-1$
							setPageComplete(false);
							return;
						}
						parent = parent.getParentFile();
					}
				}
			} else
				workDir.setText("No Repository selected"); //$NON-NLS-1$
			setPageComplete(selectedRepository != null
					&& projectMoveViewer.getCheckedElements().length > 0);
		} else {
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
