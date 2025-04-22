			git2.branchCreate().setName("refs/heads/other").call();
			git2.checkout().setName("refs/heads/other").call();

			writeTrashFile("a"
			git2.add().addFilepattern("a").call();
			RevCommit commit2 = git2.commit().setMessage("adding a").call();

			Properties res = git1.gc().setExpire(null).call();
			assertEquals(7

			writeTrashFile("b"
			git1.add().addFilepattern("b").call();
			RevCommit commit3 = git1.commit().setMessage("adding b").call();

			try {
				git1.push().setRemote("test").setRefSpecs(spec).call();
			} catch (TransportException e) {
				assertTrue("should be caused by a MissingObjectException"
						.getCause().getCause() instanceof MissingObjectException);
				fail("caught MissingObjectException for a change we don't have");
			}

			try {
				db.resolve(commit2.getId().getName() + "^{commit}");
				fail("id shouldn't exist locally");
			} catch (MissingObjectException e) {
			}
			assertEquals(commit2.getId()
					db2.resolve(commit2.getId().getName() + "^{commit}"));
			assertEquals(commit3.getId()
					db2.resolve(commit3.getId().getName() + "^{commit}"));
