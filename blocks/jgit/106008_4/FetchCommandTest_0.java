	@Test
	public void testFetchFromURI() throws Exception {

		RevCommit commit = remoteGit.commit().setMessage("initial commit").call();
		Ref tagRef = remoteGit.tag().setName("tag").call();
        String remoteURI = remoteGit
                .getRepository().getWorkTree().toURI().toString();

		git.fetch().setRemote(remoteURI).call();

		assertEquals(commit.getId()
				db.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tagRef.getObjectId()
				db.resolve(tagRef.getObjectId().getName()));
	}

