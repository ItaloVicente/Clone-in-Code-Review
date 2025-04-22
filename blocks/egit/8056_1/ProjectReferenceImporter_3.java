				IPath newWorkDir = new Path(repositoryPath.getAbsolutePath())
						.removeLastSegments(1);
				List<IProject> p = importProjects(projects, newWorkDir,
						repositoryPath, monitor);
				importedProjects.addAll(p);
			}
		}
		return importedProjects;
	}
