	@Test
	public void testRebaseInteractiveFixupWithBlankLines() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\n\nsome text").call();

		git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.get(1).setAction(Action.FIXUP);
					}

					public String modifyCommitMessage(String commit) {
						fail("No callback to modify commit message expected for single fixup");
						return commit;
					}
				}).call();

		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals("Add file2"
				headCommit.getFullMessage());
	}
