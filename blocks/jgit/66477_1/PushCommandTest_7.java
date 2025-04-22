			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/master"));

			git.branchCreate().setName("refs/heads/test").call();
			git.checkout().setName("refs/heads/test").call();

			for (int i = 0; i < 6; i++) {
				writeTrashFile("f" + i
				git.add().addFilepattern("f" + i).call();
				commit = git.commit().setMessage("adding f" + i).call();
				git.push().setRemote("test").call();
				git2.getRepository().getAllRefs();
				assertEquals("failed to update on attempt " + i
						git2.getRepository().resolve("refs/heads/test"));
			}
