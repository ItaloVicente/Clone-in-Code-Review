        provider.getFileSystem(newRepo);
    }

    @Test
    public void testDeleteShouldRemoveEmptyParentDir() throws IOException {

        FileSystem doraFS = provider.newFileSystem(doraRepo,
                                                   EMPTY_ENV);

        final File doraRepoDir = ((JGitFileSystemProxy) doraFS).getGit().getRepository().getDirectory();

        final File parentDir = doraRepoDir.getParentFile();
        final File gitProviderDir = provider.getGitRepoContainerDir();

        FileSystem doraFS1 = provider.newFileSystem(doraRepo1,
                                                    EMPTY_ENV);
        final File dora1RepoDir = ((JGitFileSystemProxy) doraFS1).getGit().getRepository().getDirectory();

        final File parentDir1 = doraRepoDir.getParentFile();

        assertEquals(parentDir, parentDir1);

        provider.delete(doraFS.getPath(null));
        assertFalse(doraRepoDir.exists());
        assertTrue(parentDir.exists());
        assertTrue(gitProviderDir.exists());

        provider.delete(doraFS1.getPath(null));
        assertFalse(dora1RepoDir.exists());
        assertTrue(parentDir1.exists());
        assertTrue(gitProviderDir.exists());
    }

    @Test
    public void testDelete() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

