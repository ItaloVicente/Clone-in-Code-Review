		try {
			GitURI gitURI = new GitURI(uri);
			return asReference(gitURI.getRepository().toString(),
					gitURI.getTag(), gitURI.getPath().toString());
		} catch (IllegalArgumentException e) {
			Activator.error(e.getMessage(), e);
			return null;
		}
