		RevWalk revWalk = new RevWalk(targetRepo);
		ObjectId headId = targetRepo.resolve(Constants.HEAD);
		RevCommit root = revWalk.parseCommit(headId);
		revWalk.markStart(root);
		RevCommit head = revWalk.next();
		RevCommit beforeHead = revWalk.next();

		assertEquals(TARGET_COMMIT_MESSAGE, head.getFullMessage());
		assertEquals(SOURCE_COMMIT_MESSAGE, beforeHead.getFullMessage());

		assertFileContentsEqual(sourceFile, SOURCE_FILE_CONTENTS);
		assertFileContentsEqual(newFile, NEW_FILE_CONTENTS);
		assertEquals(RepositoryState.SAFE, target
			.getRepository().getRepositoryState());
