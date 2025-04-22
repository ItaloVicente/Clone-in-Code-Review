	}

	@Test
	public void fetchShouldAutoFollowTag() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName("foo").call();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
