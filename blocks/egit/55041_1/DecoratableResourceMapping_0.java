		if (repository.isBare()) {
			return null;
		}
		IPath location = res.getLocation();
		if (location == null) {
			return null;
		}
		return stripWorkDir(repository.getWorkTree(), location.toFile());
