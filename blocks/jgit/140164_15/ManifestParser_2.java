		if (qName != null) {
			switch (qName) {
					throw new SAXException(RepoText.get().invalidManifest);
				}
				currentProject = new RepoProject(
						attributes.getValue("name")
						attributes.getValue("path")
						attributes.getValue("revision")
						attributes.getValue("remote")
				currentProject.setRecommendShallow(
				break;
				Remote remote = new Remote(fetch
				remotes.put(attributes.getValue("name")
				if (alias != null)
					remotes.put(alias
				break;
				break;
				if (currentProject == null)
					throw new SAXException(RepoText.get().invalidManifest);
				currentProject.addCopyFile(new CopyFile(
