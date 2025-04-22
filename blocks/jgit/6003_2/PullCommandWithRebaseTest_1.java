    @Test
    public void testPullFastForwardWithLocalCommitAndRebaseFlagSet() throws Exception {
        final String SOURCE_COMMIT_MESSAGE = "Source commit message for rebase flag test";
        final String TARGET_COMMIT_MESSAGE = "Target commit message for rebase flag test";

        assertFalse(SOURCE_COMMIT_MESSAGE.equals(TARGET_COMMIT_MESSAGE));

        final String SOURCE_FILE_CONTENTS = "Source change";
        final String NEW_FILE_CONTENTS = "New file from target";

        StoredConfig targetConfig = dbTarget.getConfig();
        targetConfig.setBoolean("branch"
        targetConfig.save();

        writeToFile(sourceFile
        source.add().addFilepattern(sourceFile.getName()).call();
        source.commit().setMessage(SOURCE_COMMIT_MESSAGE).call();

        File newFile = new File(dbTarget.getWorkTree().getPath() + "/newFile.txt");
        writeToFile(newFile
        target.add().addFilepattern(newFile.getName()).call();
        target.commit().setMessage(TARGET_COMMIT_MESSAGE).call();

        assertFalse(targetConfig.getBoolean("branch"

        PullResult pullResult = target.pull().setRebase(true).call();

        assertTrue(pullResult.isSuccessful());

        RebaseResult rebaseResult = pullResult.getRebaseResult();
        assertNotNull(rebaseResult);
        assertNull(rebaseResult.getFailingPaths());
        assertEquals(Status.OK

        Repository targetRepo = target.getRepository();
        RevWalk revWalk = new RevWalk(targetRepo);
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

