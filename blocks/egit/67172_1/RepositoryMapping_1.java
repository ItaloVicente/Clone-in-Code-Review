		java.nio.file.Path gPath = gitDir.toPath();
		java.nio.file.Path lPath = location.toFile().toPath();
		if (lPath.getNameCount() > 0 && gPath.getNameCount() > 0
				&& gPath.getRoot().equals(lPath.getRoot())
				&& gPath.getName(0).equals(lPath.getName(0))) {
			gPath = lPath.relativize(gPath);
