    @Test
    public void testMergeSuccessful() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(master,
                      userBranch);

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }

        provider.copy(userBranch,
                      master,
                      new MergeCopyOption());

        final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();

        final List<DiffEntry> result = new ListDiffs(gitRepo,
                                                     new GetTreeFromRef(gitRepo,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(gitRepo,
                                                                        "user_branch").execute()).execute();

        assertThat(result.size()).isEqualTo(0);
    }

    @Test(expected = GitException.class)
    public void testMergeConflicts() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(master,
                      userBranch);

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content changed".getBytes());
            outStream.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my very cool content".getBytes());
            outStream.close();
        }

        provider.copy(userBranch,
                      master,
                      new MergeCopyOption());
    }

    @Test
    public void testMergeBinarySuccessful() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(this.loadImage("images/drools.png"));
            outStream.close();
        }


        provider.copy(master,
                      userBranch);

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write(this.loadImage("images/jbpm.png"));
            outStream2.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write(this.loadImage("images/opta.png"));
            outStream3.close();
        }

        provider.copy(userBranch,
                      master,
                      new MergeCopyOption());

        final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();
        final List<DiffEntry> result = new ListDiffs(gitRepo,
                                                     new GetTreeFromRef(gitRepo,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(gitRepo,
                                                                        "user_branch").execute()).execute();

        assertThat(result.size()).isEqualTo(0);
    }

    @Test(expected = GitException.class)
    public void testBinaryMergeConflicts() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(this.loadImage("images/drools.png"));
            outStream.close();
        }


        provider.copy(master,
                      userBranch);

        {

            final OutputStream outStream = provider.newOutputStream(path2);
            outStream.write(this.loadImage("images/jbpm.png"));
            outStream.close();
        }
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(this.loadImage("images/jbpm.png"));
            outStream.close();
        }
        {

            final OutputStream outStream = provider.newOutputStream(path3);
            outStream.write(this.loadImage("images/opta.png"));
            outStream.close();
        }
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(this.loadImage(""));
            outStream.close();
        }

        provider.copy(userBranch,
                      master,
                      new MergeCopyOption());
    }

    @Test(expected = GitException.class)
    public void testTryToMergeNonexistentBranch() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(develop,
                      master,
                      new MergeCopyOption());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingParemeter() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(develop,
                      null,
                      new MergeCopyOption());
    }
