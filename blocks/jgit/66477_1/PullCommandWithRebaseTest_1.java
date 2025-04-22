		try (RevWalk revWalk = new RevWalk(targetRepo)) {
			ObjectId headId = targetRepo.resolve(Constants.HEAD);
			RevCommit root = revWalk.parseCommit(headId);
			revWalk.markStart(root);
			RevCommit head = revWalk.next();
			RevCommit beforeHead = revWalk.next();

			assertEquals(TARGET_COMMIT_MESSAGE
			assertEquals(SOURCE_COMMIT_MESSAGE

			assertFileContentsEqual(sourceFile
			assertFileContentsEqual(newFile
			assertEquals(RepositoryState.SAFE
				.getRepository().getRepositoryState());
		}
