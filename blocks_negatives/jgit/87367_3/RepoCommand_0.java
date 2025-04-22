			Repository repo = Git
					.cloneRepository()
					.setBare(true)
					.setDirectory(dir)
					.setURI(uri)
					.call()
					.getRepository();
			try {
