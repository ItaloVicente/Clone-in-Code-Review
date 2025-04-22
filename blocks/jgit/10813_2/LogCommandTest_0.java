	@Test
	public void logAllCommitsWithTag() throws Exception {
		List<RevCommit> commits = new ArrayList<RevCommit>();
		Git git = Git.wrap(db);

			writeTrashFile("Test.txt"
			git.add().addFilepattern("Test.txt").call();
			commits.add(git.commit().setMessage("initial commit").call());
			
			TagCommand tagCmd = git.tag();
			tagCmd.setName("tagname");
			tagCmd.setObjectId(commits.get(0));
			tagCmd.setTagger(new PersonIdent(db));
			Ref tag = tagCmd.call();
			
			Iterator<RevCommit> log = git.log().all().call().iterator();
			assertTrue(log.hasNext());
			RevCommit commit = log.next();
			tag = db.peel(tag);

			assertEquals(commit.getName()
			assertTrue(commits.contains(commit));
	}

