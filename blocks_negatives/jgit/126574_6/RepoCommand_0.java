			for (RepoProject proj : filteredProjects) {
				addSubmoduleBare(proj.getUrl(), proj.getPath(),
						proj.getRevision(), proj.getCopyFiles(),
						proj.getLinkFiles(), proj.getGroups(),
						proj.getRecommendShallow());
			}
