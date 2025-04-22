
        final OutputStream outStream = provider.newOutputStream(path);
        outStream.write("my cool content".getBytes());
        outStream.close();


        final OutputStream outStream2 = provider.newOutputStream(path2);
        outStream2.write("my cool content".getBytes());
        outStream2.close();


        final OutputStream outStream3 = provider.newOutputStream(path3);
        outStream3.write("my cool content".getBytes());
        outStream3.close();

        final BasicFileAttributes attrs = provider.readAttributes(path3,
                                                                  BasicFileAttributes.class);

        assertThat(attrs.isDirectory()).isFalse();
        assertThat(attrs.isRegularFile()).isTrue();
        assertThat(attrs.creationTime()).isNotNull();
        assertThat(attrs.lastModifiedTime()).isNotNull();
        assertThat(attrs.size()).isEqualTo(15L);

        try {
                                    BasicFileAttributes.class);
            failBecauseExceptionWasNotThrown(NoSuchFileException.class);
        } catch (NoSuchFileException ignored) {
        }

        assertThat(provider.readAttributes(path3,
                                           MyAttrs.class)).isNull();


        final BasicFileAttributes attrsRoot = provider.readAttributes(rootPath,
                                                                      BasicFileAttributes.class);

        assertThat(attrsRoot.isDirectory()).isTrue();
        assertThat(attrsRoot.isRegularFile()).isFalse();
        assertThat(attrsRoot.creationTime()).isNotNull();
        assertThat(attrsRoot.lastModifiedTime()).isNotNull();
        assertThat(attrsRoot.size()).isEqualTo(-1L);
    }

    @Test
    public void testReadAttributesMap() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);


        final OutputStream outStream = provider.newOutputStream(path);
        outStream.write("my cool content".getBytes());
        outStream.close();


        final OutputStream outStream2 = provider.newOutputStream(path2);
        outStream2.write("my cool content".getBytes());
        outStream2.close();


        final OutputStream outStream3 = provider.newOutputStream(path3);
        outStream3.write("my cool content".getBytes());
        outStream3.close();

        assertThat(provider.readAttributes(path,
                                           "*")).isNotNull().hasSize(9);
        assertThat(provider.readAttributes(path,
                                           "basic:*")).isNotNull().hasSize(9);
        assertThat(provider.readAttributes(path,
                                           "basic:isRegularFile")).isNotNull().hasSize(1);
        assertThat(provider.readAttributes(path,
                                           "basic:isRegularFile,isDirectory")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(path,
                                           "basic:isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(path,
                                           "basic:someThing")).isNotNull().hasSize(0);
        assertThat(provider.readAttributes(path,
                                           "version:version")).isNotNull().hasSize(1);

        assertThat(provider.readAttributes(path,
                                           "isRegularFile")).isNotNull().hasSize(1);
        assertThat(provider.readAttributes(path,
                                           "isRegularFile,isDirectory")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(path,
                                           "isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(path,
                                           "someThing")).isNotNull().hasSize(0);

        try {
            provider.readAttributes(path,
                                    ":someThing");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException ignored) {
        }

        try {
            provider.readAttributes(path,
                                    "advanced:isRegularFile");
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ignored) {
        }


        assertThat(provider.readAttributes(rootPath,
                                           "*")).isNotNull().hasSize(9);
        assertThat(provider.readAttributes(rootPath,
                                           "basic:*")).isNotNull().hasSize(9);
        assertThat(provider.readAttributes(rootPath,
                                           "basic:isRegularFile")).isNotNull().hasSize(1);
        assertThat(provider.readAttributes(rootPath,
                                           "basic:isRegularFile,isDirectory")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(rootPath,
                                           "basic:isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(rootPath,
                                           "basic:someThing")).isNotNull().hasSize(0);

        assertThat(provider.readAttributes(rootPath,
                                           "isRegularFile")).isNotNull().hasSize(1);
        assertThat(provider.readAttributes(rootPath,
                                           "isRegularFile,isDirectory")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(rootPath,
                                           "isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
        assertThat(provider.readAttributes(rootPath,
                                           "someThing")).isNotNull().hasSize(0);

        try {
            provider.readAttributes(rootPath,
                                    ":someThing");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException ignored) {
        }

        try {
            provider.readAttributes(rootPath,
                                    "advanced:isRegularFile");
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ignored) {
        }

        try {
                                    BasicFileAttributes.class);
            failBecauseExceptionWasNotThrown(NoSuchFileException.class);
        } catch (NoSuchFileException ignored) {
        }
    }

    @Test
    public void testSetAttribute() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);


        final OutputStream outStream = provider.newOutputStream(path);
        outStream.write("my cool content".getBytes());
        outStream.close();


        final OutputStream outStream2 = provider.newOutputStream(path2);
        outStream2.write("my cool content".getBytes());
        outStream2.close();


        final OutputStream outStream3 = provider.newOutputStream(path3);
        outStream3.write("my cool content".getBytes());
        outStream3.close();

        try {
            provider.setAttribute(path3,
                                  "basic:isRegularFile",
                                  true);
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ignored) {
        }

        try {
            provider.setAttribute(path3,
                                  "isRegularFile",
                                  true);
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ignored) {
        }

        try {
            provider.setAttribute(path3,
                                  "notExisits",
                                  true);
            failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException ignored) {
        }

        try {
            provider.setAttribute(path3,
                                  "advanced:notExisits",
                                  true);
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ignored) {
        }

        try {
            provider.setAttribute(path3,
                                  ":isRegularFile",
                                  true);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private static class MyInvalidFileAttributeView implements BasicFileAttributeView {

        @Override
        public BasicFileAttributes readAttributes() throws IOException {
            return null;
        }

        @Override
        public void setTimes(FileTime lastModifiedTime,
                             FileTime lastAccessTime,
                             FileTime createTime) throws IOException {

        }

        @Override
        public String name() {
            return null;
        }
    }

    @Test
    public void checkProperAmend() throws Exception {


        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     new HashMap<String, Object>() {{
                                                         put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT,
                                                             "true");
                                                     }});

        assertThat(fs).isNotNull();

        for (int z = 0; z < 5; z++) {
            provider.setAttribute(_path,
                                  FileSystemState.FILE_SYSTEM_STATE_ATTR,
                                  FileSystemState.BATCH);
            {
                final OutputStream outStream = provider.newOutputStream(path);
                assertThat(outStream).isNotNull();
                outStream.write(("my cool content" + z).getBytes());
                outStream.close();
            }
            {
                final OutputStream outStream2 = provider.newOutputStream(path2);
                assertThat(outStream2).isNotNull();
                outStream2.write(("bad content" + z).getBytes());
                outStream2.close();
            }

            provider.setAttribute(_path,
                                  FileSystemState.FILE_SYSTEM_STATE_ATTR,
                                  FileSystemState.NORMAL);
        }

        final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path.getRoot(),
                                                                                 JGitVersionAttributeViewImpl.class);

        assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);
    }

    @Test
    public void accessOldVersions() throws Exception {


        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     new HashMap<String, Object>() {{
                                                         put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT,
                                                             "true");
                                                     }});

        assertThat(fs).isNotNull();

        for (int i = 0; i < 5; i++) {
            final OutputStream outStream = provider.newOutputStream(path);
            assertThat(outStream).isNotNull();
            outStream.write(("my cool content" + i).getBytes());
            outStream.close();
        }

        final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path,
                                                                                 JGitVersionAttributeViewImpl.class);

        assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);

        for (int i = 0; i < 5; i++) {
            final InputStream stream = provider.newInputStream(oldPath);
            assertNotNull(stream);
            final String content = new Scanner(stream).useDelimiter("\\A").next();
            assertEquals("my cool content" + i,
                         content);
        }
    }

    @Test
    public void checkProperSquash() throws IOException, GitAPIException {

        final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo,
                                                                          EMPTY_ENV);


        final OutputStream aStream = provider.newOutputStream(path);
        aStream.write("my cool content".getBytes());
        aStream.close();
        final RevCommit commit = ((GitImpl) fs.getGit())._log().add(fs.getGit().getRef("master").getObjectId()).setMaxCount(1).call().iterator().next();

        final OutputStream bStream = provider.newOutputStream(path2);
        bStream.write("my cool content".getBytes());
        bStream.close();
        final OutputStream cStream = provider.newOutputStream(path3);
        cStream.write("my cool content".getBytes());
        cStream.close();

        final VersionRecord record = makeVersionRecord("aparedes",
                                                       "aparedes@redhat.com",
                                                       "squashing!",
                                                       new Date(),
                                                       commit.getName());
        final SquashOption squashOption = new SquashOption(record);

        provider.setAttribute(generalPath,
                              SquashOption.SQUASH_ATTR,
                              squashOption);

        int commitsCount = 0;
        for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
            commitsCount++;
            System.out.println(com.getName() + " - " + com.getFullMessage());
        }
        assertThat(commitsCount).isEqualTo(2);
    }

    @Test(expected = GitException.class)
    public void testSquashFailBecauseCommitIsFromAnotherBranch() throws IOException, GitAPIException {

        final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo,
                                                                          EMPTY_ENV);


        final OutputStream aStream = provider.newOutputStream(path);
        aStream.write("my cool content".getBytes());
        aStream.close();

        final List<RevCommit> commits = getCommitsFromBranch((GitImpl) fs.getGit(),
                                                             "develop");

        final OutputStream bStream = provider.newOutputStream(path2);
        bStream.write("my cool content".getBytes());
        bStream.close();
        final OutputStream cStream = provider.newOutputStream(path3);
        cStream.write("my cool content".getBytes());
        cStream.close();

        final VersionRecord record = makeVersionRecord("aparedes",
                                                       "aparedes@redhat.com",
                                                       "squashing!",
                                                       new Date(),
                                                       commits.get(0).getName());
        final SquashOption squashOption = new SquashOption(record);

        provider.setAttribute(generalPath,
                              SquashOption.SQUASH_ATTR,
                              squashOption);
    }

    @Test
    public void checkBatchError() throws Exception {

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     new HashMap<String, Object>() {{
                                                         put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT,
                                                             "true");
                                                     }});

        provider = spy(provider);

        doThrow(new RuntimeException()).
                when(provider).
                notifyDiffs(any(JGitFileSystemImpl.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(ObjectId.class),
                            any(ObjectId.class));

        assertThat(fs).isNotNull();

        provider.setAttribute(path,
                              FileSystemState.FILE_SYSTEM_STATE_ATTR,
                              FileSystemState.BATCH);
        final OutputStream outStream = provider.newOutputStream(path);
        assertThat(outStream).isNotNull();
        outStream.write(("my cool content").getBytes());
        outStream.close();

        try {
            provider.setAttribute(path,
                                  FileSystemState.FILE_SYSTEM_STATE_ATTR,
                                  FileSystemState.NORMAL);
        } catch (Exception ex) {
            fail("Batch can't fail!",
                 ex);
        }
    }

    @Test
    public void resolveFSName() {

        String fsName = "dora-repo";
        assertEquals(fsName,
        assertEquals(fsName,

        assertEquals(fsName,
        assertEquals(fsName,

        fsName = "dora-repo/subdir";
        assertEquals(fsName,
        assertEquals("dora-repo/subdir",

        assertEquals("dora-repo/subdir",
        assertEquals("dora-repo/subdir",

        fsName = "dora-repo/subdir/subdir";
        assertEquals(fsName,
        assertEquals(fsName,

        assertEquals(fsName,
        assertEquals(fsName,
    }

    @Test
    public void resolveSimpleFSNames() throws IOException {


        try {
            fail("should triggered FileSystemNotFoundException");
        } catch (FileSystemNotFoundException e) {
        }

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     EMPTY_ENV);

        assertThat(fs).isNotNull();


        assertEquals(fs,
                     path.getFileSystem());
        assertEquals(path.getFileSystem(),
                     another.getFileSystem());
    }

    @Test
    public void resolveComposedFSNames() throws IOException {


        final FileSystem fsSimpleName = provider.newFileSystem(simpleName,
                                                               EMPTY_ENV);

        assertThat(fsSimpleName).isNotNull();

