			assertEquals(MergeStatus.CONFLICTING
			assertEquals(result.getConflicts().keySet()
					Collections.singleton(FILE1));
			writeTrashFile(FILE1
			git.add().addFilepattern(FILE1).call();
			RevCommit d = git.commit().setMessage("commit d").call();

			RevCommit f = commitFile("file2"

			checkoutBranch("refs/heads/master");
			writeTrashFile("fileg"
			if (testConflict)
				writeTrashFile("conflict"
			git.add().addFilepattern(".").call();
			RevCommit g = git.commit().setMessage("commit g").call();

			checkoutBranch("refs/heads/topic");
			RebaseResult res = git.rebase().setUpstream("refs/heads/master")
					.setPreserveMerges(true).call();
			if (testConflict) {
				assertEquals(Status.STOPPED
				assertEquals(Collections.singleton("conflict")
						.getConflicting());
				writeTrashFile("conflict"
				git.add().addFilepattern("conflict").call();
				res = git.rebase().setOperation(Operation.CONTINUE).call();
			}
			assertEquals(Status.OK

			assertEquals("merge resolution"
			assertEquals("new content two"
			assertEquals("more changess"
			assertEquals("fileg"

			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			RevCommit newF = rw.next();
			assertDerivedFrom(newF
			RevCommit newD = rw.next();
			assertDerivedFrom(newD
			assertEquals(2
			RevCommit newC = rw.next();
			assertDerivedFrom(newC
			RevCommit newE = rw.next();
			assertEquals(e
			assertEquals(newC
			assertEquals(e
			assertEquals(g
			assertEquals(b
			assertEquals(a
