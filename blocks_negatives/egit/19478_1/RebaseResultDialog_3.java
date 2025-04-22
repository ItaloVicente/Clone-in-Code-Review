					IPath filePath = repoWorkdirPath.append(repoPath);
					for (IProject project : validProjects)
						if (project.getLocation().isPrefixOf(filePath)) {
							IResource res = project.getFile(filePath
									.removeFirstSegments(project.getLocation()
											.segmentCount()));
							resourceList.add(res);
						}
