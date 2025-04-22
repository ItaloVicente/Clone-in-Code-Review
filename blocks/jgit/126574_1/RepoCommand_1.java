
		Map<String
		for (RepoProject proj : projects) {
			if (m.containsKey(proj.getName())) {
				m.get(proj.getName()).add(proj);
			} else {
				List<RepoProject> l = new ArrayList<>();
				l.add(proj);
				m.put(proj.getName()
			}
		}

		List<RepoProject> ret = new ArrayList<>();
		for (Entry<String
			boolean suffixWithPath = e.getValue().size() != 1;
			for (RepoProject proj : e.getValue()) {
				String name = proj.getName();
				if (suffixWithPath) {
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
