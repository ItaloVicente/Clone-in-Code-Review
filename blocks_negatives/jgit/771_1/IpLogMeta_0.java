		for (String id : cfg.getSubsections(S_PROJECT)) {
			String name = cfg.getString(S_PROJECT, id, K_NAME);
			Project project = new Project(id, name);
			project.setComments(cfg.getString(S_PROJECT, id, K_COMMENTS));

			for (String c : cfg.getStringList(S_PROJECT, id, K_SKIP_COMMIT))
				project.addSkipCommit(ObjectId.fromString(c));
			for (String license : cfg.getStringList(S_PROJECT, id, K_LICENSE))
				project.addLicense(license);
			projects.add(project);
		}
