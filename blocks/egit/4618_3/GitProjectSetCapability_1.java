	@Override
	public String asReference(URI uri, String projectName) {
		GitURI gitURI = GitURI.fromUri(uri);
		return asReference(gitURI.getRepository().toString(), gitURI.getTag(), gitURI.getPath().toString());
	}

