		}
		GitProjectData projectData = GitProjectData
				.get(mappedContainer.getProject());
		if (projectData == null) {
			return null;
		}
