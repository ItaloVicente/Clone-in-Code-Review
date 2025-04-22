		CheckoutCommand command = new Git(db).checkout();
		if (paths.size() > 0) {
			command.setStartPoint(name);
			for (String path : paths)
				command.addPath(path);
		} else {
			command.setCreateBranch(createBranch);
			command.setName(name);
			command.setForce(force);
			command.setOrphan(orphan);
		}
		try {
			String oldBranch = db.getBranch();
			Ref ref = command.call();
			if (ref == null)
				return;
			if (Repository.shortenRefName(ref.getName()).equals(oldBranch)) {
