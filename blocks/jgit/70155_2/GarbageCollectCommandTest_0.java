
	@Test
	public void testPruneOldOrphanCommit() throws Exception {
		ObjectId initial = git.getRepository().resolve("HEAD");
		RevCommit orphan = git.commit().setMessage("orphan")
				.setReflogComment(null).call();
		RefUpdate refUpdate = git.getRepository()
				.updateRef("refs/heads/master");
		refUpdate.setNewObjectId(initial);
		refUpdate.forceUpdate();

		git.gc().setExpire(new Date()).call();
		assertFalse(
				git.getRepository().getObjectDatabase().has(orphan.getId()));
	}
