		if (directoriesVisited == null) {
			directoriesVisited = new HashSet<>();
			try {
				directoriesVisited.add(directory.getCanonicalPath());
			} catch (IOException exception) {
				StatusManager.getManager().handle(StatusUtil.newError(exception));
			}
