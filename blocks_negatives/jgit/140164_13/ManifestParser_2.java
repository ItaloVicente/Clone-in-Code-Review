				throw new SAXException(RepoText.get().invalidManifest);
			}
			currentProject = new RepoProject(
					attributes.getValue("name"), //$NON-NLS-1$
					attributes.getValue("path"), //$NON-NLS-1$
					attributes.getValue("revision"), //$NON-NLS-1$
					attributes.getValue("remote"), //$NON-NLS-1$
			currentProject.setRecommendShallow(
			Remote remote = new Remote(fetch, revision);
			remotes.put(attributes.getValue("name"), remote); //$NON-NLS-1$
			if (alias != null)
				remotes.put(alias, remote);
			if (currentProject == null)
				throw new SAXException(RepoText.get().invalidManifest);
			currentProject.addCopyFile(new CopyFile(
