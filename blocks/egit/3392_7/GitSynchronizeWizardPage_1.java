					Repository repo = (Repository) element;
					String descr = ""; //$NON-NLS-1$
					try {
						descr += " [" + repo.getBranch() + "]";//$NON-NLS-1$ //$NON-NLS-2$
					} catch (IOException e) {
						Activator.logError(e.getMessage(), e);
					}

					Color decorationsColor = JFaceResources.getColorRegistry()
							.get(JFacePreferences.DECORATIONS_COLOR);

					String repoName = repo.getWorkTree().getName();
					int repoNameLen = repoName.length();
					StyleRange styleRange = new StyleRange(repoNameLen,
							repoNameLen + descr.length(), decorationsColor,
							null);

					cell.setImage(repositoryImage);
					cell.setText(repoName + descr);
					cell.setStyleRanges(new StyleRange[] { styleRange });
