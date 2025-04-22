		if (meta.getProjects().isEmpty()) {
			throw new ConfigInvalidException("Configuration file "
					+ log.getPathString() + " in commit " + commit.name()
					+ " has no projects declared.");
		}

