    @Test
    public void testCleanDirsWithSubmodule() throws Exception {
        SubmoduleAddCommand command = new SubmoduleAddCommand(db);
        String path = "sub";
        command.setPath(path);
        String uri = db.getDirectory().toURI().toString();
        command.setURI(uri);
        Repository repo = command.call();
        repo.close();

        Status beforeCleanStatus = git.status().call();
        assertTrue(beforeCleanStatus.getAdded().contains(DOT_GIT_MODULES));
        assertTrue(beforeCleanStatus.getAdded().contains(path));

        Set<String> cleanedFiles = git.clean().setCleanDirectories(true)
                .call();

        assertTrue(!cleanedFiles.contains(path + "/"));

        assertTrue(cleanedFiles.contains("File2.txt"));
        assertTrue(cleanedFiles.contains("File3.txt"));
        assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
        assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
        assertTrue(cleanedFiles.contains("sub-clean/"));
        assertTrue(cleanedFiles.size() == 4);
    }
