		if (paths.size() > 0) {
			command.setStartPoint(name);
			for (String path : paths)
				command.addPath(path);
		} else {
			command.setCreateBranch(createBranch);
			command.setName(name);
			command.setForce(force);
		}
