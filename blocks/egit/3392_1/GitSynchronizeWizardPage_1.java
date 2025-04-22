			public void update(ViewerCell cell) {
				Object element = cell.getElement();
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					String projectName = project.getName();
					int projNameLen = projectName.length();

					Repository repo = projects.get(project);
					String descr = " [" + repo.getWorkTree().getName(); //$NON-NLS-1$
					try {
						descr +=  " " + repo.getBranch() + "]";//$NON-NLS-1$ //$NON-NLS-2$
					} catch (IOException e) {
						Activator.logError(e.getMessage(), e);
						descr += "]"; //$NON-NLS-1$
					}

					Color decorationsColor = JFaceResources.getColorRegistry()
							.get(JFacePreferences.DECORATIONS_COLOR);

					StyleRange styleRange = new StyleRange(projNameLen,
							projNameLen + descr.length(), decorationsColor,
							null);

					cell.setImage(projectImage);
					cell.setText(projectName + descr);
					cell.setStyleRanges(new StyleRange[] {styleRange});
