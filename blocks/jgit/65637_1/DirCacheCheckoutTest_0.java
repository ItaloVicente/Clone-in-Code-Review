		try (Git git = new Git(db)) {
			writeTrashFile("f"
			writeTrashFile("D/g"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("inital").call();
			assertIndex(mkmap("f"

			git.branchCreate().setName("topic").call();

			writeTrashFile("f"
			writeTrashFile("D/g"
			writeTrashFile("E/h"
			git.add().addFilepattern(".").call();
			RevCommit master = git.commit().setMessage("master-1").call();
			assertIndex(mkmap("f"

			checkoutBranch("refs/heads/topic");
			assertIndex(mkmap("f"

			writeTrashFile("f"
			assertTrue(new File(db.getWorkTree()
			writeTrashFile("G/i"
			git.add().addFilepattern(".").call();
			git.add().addFilepattern(".").setUpdate(true).call();
			RevCommit topic = git.commit().setMessage("topic-1").call();
			assertIndex(mkmap("f"

			writeTrashFile("untracked"

			resetHard(master);
			assertIndex(mkmap("f"
			resetHard(topic);
			assertIndex(mkmap("f"
			assertWorkDir(mkmap("f"
					"untracked"));

			assertEquals(MergeStatus.CONFLICTING
					.call().getMergeStatus());
			assertEquals(
					"[D/g
					indexState(0));

			resetHard(master);
			assertIndex(mkmap("f"
			assertWorkDir(mkmap("f"
					"h()"
		}
