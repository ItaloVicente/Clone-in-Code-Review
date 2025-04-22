	@Override
	public String asReference(URI uri, String projectName) {
		GitURI gitURI = new GitURI(uri);
		return asReference(gitURI.getRepository().toString(), gitURI.getTag(), gitURI.getPath().toString());
	}

