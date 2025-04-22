					if (project.getLocation().isPrefixOf(path)) {
						final IPath projectRelativePath = path
								.removeFirstSegments(project.getLocation()
										.segmentCount());
						resourcesInOperation.add(project
								.getFile(projectRelativePath));
