
		Map<String
		for (RepoProject proj : projects) {
			List<RepoProject> l = m.get(proj.getName());
			if (l == null) {
				l = new ArrayList<>();
				m.put(proj.getName()
			}
			l.add(proj);
		}

		List<RepoProject> ret = new ArrayList<>();
		for (List<RepoProject> ps : m.values()) {
			boolean nameConflict = ps.size() != 1;
			for (RepoProject proj : ps) {
				String name = proj.getName();
				if (nameConflict) {
					name += SLASH + proj.getPath();
				}
				RepoProject bareProj = new RepoProject(name
						proj.getPath()
						proj.getGroups()
				bareProj.setUrl(proj.getUrl());
				bareProj.addCopyFiles(proj.getCopyFiles());
				bareProj.addLinkFiles(proj.getLinkFiles());
				ret.add(bareProj);
			}
		}
		return ret;
