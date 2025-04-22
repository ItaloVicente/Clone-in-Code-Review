				if (element instanceof ProjectRecord) {
					ProjectRecord p = (ProjectRecord) element;
					if (wordMatches(p.getProjectName()))
						return true;
					String projectPath = p.getProjectSystemFile().getParent();
					if (projectPath.startsWith(lastPath)) {
						String distinctPath = projectPath.substring(lastPath
								.length());
						return wordMatches(distinctPath);
					} else {
						return wordMatches(projectPath);
					}
				}

				return false;
