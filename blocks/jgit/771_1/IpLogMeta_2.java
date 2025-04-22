	private List<Project> parseProjects(final Config cfg
			final String sectionName) {
		final List<Project> dst = new ArrayList<Project>();
		for (String id : cfg.getSubsections(sectionName)) {
			String name = cfg.getString(sectionName
			Project project = new Project(id
			project.setVersion(cfg.getString(sectionName
			project.setComments(cfg.getString(sectionName

			for (String c : cfg.getStringList(sectionName
				project.addSkipCommit(ObjectId.fromString(c));
			for (String license : cfg.getStringList(sectionName
				project.addLicense(license);
			dst.add(project);
		}
		return dst;
	}

