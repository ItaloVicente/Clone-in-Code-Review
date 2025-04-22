						}
					} else {
						IPath locationPath = project.getLocation();
						if (locationPath != null) {
							File locationDir = locationPath.toFile();
							File dotGit2 = new File(locationDir,
									Constants.DOT_GIT);
							if (dotGit2.exists() && dotGit2.isDirectory()) {
								GitProjectData.reconfigureWindowCache();
