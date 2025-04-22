					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit bCommit = git.commit().setMessage("on branchtopush")
					.call();
			git.checkout().setName("master").call();
			writeTrashFile("m"
			git.add().addFilepattern("m").call();
			RevCommit mCommit = git.commit().setMessage("on master").call();
			Iterable<PushResult> result = git.push().setRemote("test")
					.setPushDefault(PushDefault.MATCHING)
					.call();
			int n = 0;
			for (PushResult r : result) {
				n++;
				assertEquals(1
				assertEquals(2
				for (RemoteRefUpdate update : r.getRemoteUpdates()) {
					assertFalse(update.isMatching());
					assertTrue(update.getSrcRef()
							.equals("refs/heads/branchtopush")
							|| update.getSrcRef().equals("refs/heads/master"));
					assertEquals(RemoteRefUpdate.Status.OK
				}
			}
			assertEquals(bCommit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(mCommit.getId()
