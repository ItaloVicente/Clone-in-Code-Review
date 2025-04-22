			bareProjects = new ArrayList<>();
			if (author == null)
				author = new PersonIdent(repo);
			if (callback == null)
				callback = new DefaultRemoteReader();
			for (RepoProject proj : filteredProjects) {
				addSubmoduleBare(proj.getUrl()
						proj.getRevision()
						proj.getLinkFiles()
						proj.getRecommendShallow());
			}
