
    public void logInit(Git git) throws GitAPIException
        File file = new File(db.getWorkTree()
        FileUtils.createNewFile(file);
        try (PrintWriter writer = new PrintWriter(file
            writer.print("content1");
        }

        git.add().addFilepattern("a.txt").call();
        git.commit().setMessage("commit1").setCommitter(committer).call();

        file = new File(db.getWorkTree()
        FileUtils.createNewFile(file);
        try (PrintWriter writer = new PrintWriter(file
            writer.print("content2");
        }

        git.add().addFilepattern("b.txt").call();
        git.commit().setMessage("commit2").setCommitter(committer).call();

        Path includeSubdir = Paths.get(db.getWorkTree().toString()
        includeSubdir.toFile().mkdirs();
        file = Paths.get(includeSubdir.toString()
        FileUtils.createNewFile(file);
        try (PrintWriter writer = new PrintWriter(file
            writer.print("content3");
        }

        git.add().addFilepattern("subdir-include").call();
        git.commit().setMessage("commit3").setCommitter(committer).call();

        Path excludeSubdir = Paths.get(db.getWorkTree().toString()
        excludeSubdir.toFile().mkdirs();
        file = Paths.get(excludeSubdir.toString()
        FileUtils.createNewFile(file);
        try (PrintWriter writer = new PrintWriter(file
            writer.print("content4");
        }

        git.add().addFilepattern("subdir-exclude").call();
        git.commit().setMessage("commit4").setCommitter(committer).call();
    }

    @Test
    public void testSomeCommits() throws JGitInternalException
        GitAPIException {

        try (Git git = new Git(db)) {
            git.commit().setMessage("initial commit").call();
            git.commit().setMessage("second commit").setCommitter(committer).call();
            git.commit().setMessage("third commit").setAuthor(author).call();
            git.commit().setMessage("fourth commit").setAuthor(author)
                .setCommitter(committer).call();
            Iterable<RevCommit> commits = git.log().call();

            PersonIdent defaultCommitter = new PersonIdent(db);
            PersonIdent expectedAuthors[] = new PersonIdent[] {defaultCommitter
                committer
            PersonIdent expectedCommitters[] = new PersonIdent[] {
                defaultCommitter
            String expectedMessages[] = new String[] {"initial commit"
                "second commit"
            int l = expectedAuthors.length - 1;
            for (RevCommit c : commits) {
                assertEquals(expectedAuthors[l].getName()
                    .getName());
                assertEquals(expectedCommitters[l].getName()
                    .getName());
                assertEquals(c.getFullMessage()
                l--;
            }
            assertEquals(l
            ReflogReader reader = db.getReflogReader(Constants.HEAD);
            assertTrue(reader.getLastEntry().getComment().startsWith("commit:"));
            reader = db.getReflogReader(db.getBranch());
            assertTrue(reader.getLastEntry().getComment().startsWith("commit:"));
        }
    }

    @Test
    public void testLogWithFilterCanDistinguishFilesByPath() throws IOException
        JGitInternalException

        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            for (RevCommit c : git.log().addPath("a.txt").call()) {
                assertEquals("commit1"
                count++;
            }
            assertEquals(1

            count = 0;
            for (RevCommit c : git.log().addPath("b.txt").call()) {
                assertEquals("commit2"
                count++;
            }
            assertEquals(1
        }
    }


    @Test
    public void testLogWithFilterCanIncludeFilesInDirectory() throws IOException
        JGitInternalException
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            for (RevCommit c : git.log().addPath("subdir-include").call()) {
                assertEquals("commit3"
                count++;
            }
            assertEquals(1
        }
    }


    @Test
    public void testLogWithFilterCanExcludeFilesInDirectory() throws IOException
        JGitInternalException
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            Iterator it = git.log()
                .excludePath("subdir-exclude")
                .call()
                .iterator();
            while (it.hasNext()) {
                it.next();
                count++;
            }
            assertEquals(3
        }
    }

    @Test
    public void testLogWithoutFilter() throws IOException
        GitAPIException {
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            for (RevCommit c : git.log().call()) {
                assertEquals(committer
                count++;
            }
            assertEquals(4
        }
    }

    @Test
    public void testLogWithFilterCanExcludeAndIncludeFilesInDifferentDirectories() throws
        IOException
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            Iterator it = git.log()
                .addPath("subdir-include")
                .excludePath("subdir-exclude")
                .call()
                .iterator();
            while (it.hasNext()) {
                it.next();
                count++;
            }
            assertEquals(1
        }
    }

    @Test
    public void testLogWithFilterExcludeAndIncludeSameFileIncludesNothing() throws IOException
        JGitInternalException
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            Iterator it = git.log()
                .addPath("subdir-exclude")
                .excludePath("subdir-exclude")
                .call()
                .iterator();

            while (it.hasNext()) {
                it.next();
                count++;
            }
            assertEquals(0
        }
    }


    @Test
    public void testLogWithFilterCanExcludeFileAndDirectory() throws IOException
        JGitInternalException
        try (Git git = new Git(db)) {
            logInit(git);
            int count = 0;
            Iterator it = git.log()
                .excludePath("b.txt")
                .excludePath("subdir-exclude")
                .call()
                .iterator();

            while (it.hasNext()) {
                it.next();
                count++;
            }
            assertEquals(2
        }
    }

    @Test
    public void testWrongParams() throws GitAPIException {
        try (Git git = new Git(db)) {
            git.commit().setAuthor(author).call();
            fail("Didn't get the expected exception");
        } catch (NoMessageException e) {
        }
    }

    @Test
    public void testMultipleInvocations() throws GitAPIException {
        try (Git git = new Git(db)) {
            CommitCommand commitCmd = git.commit();
            commitCmd.setMessage("initial commit").call();
            try {
                commitCmd.setAuthor(author);
                fail("didn't catch the expected exception");
            } catch (IllegalStateException e) {
            }
            LogCommand logCmd = git.log();
            logCmd.call();
            try {
                logCmd.call();
                fail("didn't catch the expected exception");
            } catch (IllegalStateException e) {
            }
        }
    }

    @Test
    public void testMergeEmptyBranches() throws IOException
        JGitInternalException
        try (Git git = new Git(db)) {
            git.commit().setMessage("initial commit").call();
            RefUpdate r = db.updateRef("refs/heads/side");
            r.setNewObjectId(db.resolve(Constants.HEAD));
            assertEquals(r.forceUpdate()
            RevCommit second = git.commit().setMessage("second commit").setCommitter(committer).call();
            db.updateRef(Constants.HEAD).link("refs/heads/side");
            RevCommit firstSide = git.commit().setMessage("first side commit").setAuthor(author).call();

            write(new File(db.getDirectory()
                .toString(db.resolve("refs/heads/master")));
            write(new File(db.getDirectory()

            RevCommit commit = git.commit().call();
            RevCommit[] parents = commit.getParents();
            assertEquals(parents[0]
            assertEquals(parents[1]
            assertEquals(2
        }
    }

    @Test
    public void testAddUnstagedChanges() throws IOException
        JGitInternalException
        File file = new File(db.getWorkTree()
        FileUtils.createNewFile(file);
        try (PrintWriter writer = new PrintWriter(file
            writer.print("content");
        }

        try (Git git = new Git(db)) {
            git.add().addFilepattern("a.txt").call();
            RevCommit commit = git.commit().setMessage("initial commit").call();
            TreeWalk tw = TreeWalk.forPath(db
            assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
                tw.getObjectId(0).getName());

            try (PrintWriter writer = new PrintWriter(file
                writer.print("content2");
            }
            commit = git.commit().setMessage("second commit").call();
            tw = TreeWalk.forPath(db
            assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
                tw.getObjectId(0).getName());

            commit = git.commit().setAll(true).setMessage("third commit")
                .setAll(true).call();
            tw = TreeWalk.forPath(db
            assertEquals("db00fd65b218578127ea51f3dffac701f12f486a"
                tw.getObjectId(0).getName());
        }
    }

    @Test
    public void testModeChange() throws IOException
        try (Git git = new Git(db)) {
            File file = new File(db.getWorkTree()
            FileUtils.createNewFile(file);
            try (PrintWriter writer = new PrintWriter(file
                writer.print("content1");
            }

            git.add().addFilepattern("a.txt").call();
            git.commit().setMessage("commit1").setCommitter(committer).call();

            FS fs = db.getFS();
            fs.setExecute(file
            git.add().addFilepattern("a.txt").call();
            git.commit().setMessage("mode change").setCommitter(committer).call();

            fs.setExecute(file
            git.add().addFilepattern("a.txt").call();
            git.commit().setMessage("mode change").setCommitter(committer)
                .setOnly("a.txt").call();
        }
    }

    @Test
    public void testCommitRange() throws GitAPIException
        JGitInternalException
        IncorrectObjectTypeException {
        try (Git git = new Git(db)) {
            git.commit().setMessage("first commit").call();
            RevCommit second = git.commit().setMessage("second commit")
                .setCommitter(committer).call();
            git.commit().setMessage("third commit").setAuthor(author).call();
            RevCommit last = git.commit().setMessage("fourth commit").setAuthor(
                author)
                .setCommitter(committer).call();
            Iterable<RevCommit> commits = git.log().addRange(second.getId()
                last.getId()).call();

            PersonIdent defaultCommitter = new PersonIdent(db);
            PersonIdent expectedAuthors[] = new PersonIdent[] {author
            PersonIdent expectedCommitters[] = new PersonIdent[] {
                defaultCommitter
            String expectedMessages[] = new String[] {"third commit"
                "fourth commit"};
            int l = expectedAuthors.length - 1;
            for (RevCommit c : commits) {
                assertEquals(expectedAuthors[l].getName()
                    .getName());
                assertEquals(expectedCommitters[l].getName()
                    .getName());
                assertEquals(c.getFullMessage()
                l--;
            }
            assertEquals(l
        }
    }

    @Test
    public void testCommitAmend() throws JGitInternalException
        GitAPIException {
        try (Git git = new Git(db)) {
            git.commit().setAmend(true).setMessage("first commit").call();

            Iterable<RevCommit> commits = git.log().call();
            int c = 0;
            for (RevCommit commit : commits) {
                assertEquals("first commit"
                c++;
            }
            assertEquals(1
            ReflogReader reader = db.getReflogReader(Constants.HEAD);
            assertTrue(reader.getLastEntry().getComment()
                .startsWith("commit (amend):"));
            reader = db.getReflogReader(db.getBranch());
            assertTrue(reader.getLastEntry().getComment()
                .startsWith("commit (amend):"));
        }
    }

    @Test
    public void testInsertChangeId() throws JGitInternalException
        GitAPIException {
        try (Git git = new Git(db)) {
            String messageHeader = "Some header line\n\nSome detail explanation\n";
            String changeIdTemplate = "\nChange-Id: I"
                + ObjectId.zeroId().getName() + "\n";
            String messageFooter = "Some foooter lines\nAnother footer line\n";
            RevCommit commit = git.commit().setMessage(
                messageHeader + messageFooter)
                .setInsertChangeId(true).call();
            byte[] chars = commit.getFullMessage().getBytes(UTF_8);
            int lastLineBegin = RawParseUtils.prevLF(chars
            String lastLine = RawParseUtils.decode(chars
                chars.length);
            assertTrue(lastLine.contains("Change-Id:"));
            assertFalse(lastLine.contains(
                "Change-Id: I" + ObjectId.zeroId().getName()));

            commit = git.commit().setMessage(
                messageHeader + changeIdTemplate + messageFooter)
                .setInsertChangeId(true).call();
            chars = commit.getFullMessage().getBytes(UTF_8);
            int lineStart = 0;
            int lineEnd = 0;
            for (int i = 0; i < 4; i++) {
                lineStart = RawParseUtils.nextLF(chars
            }
            lineEnd = RawParseUtils.nextLF(chars

            String line = RawParseUtils.decode(chars

            assertTrue(line.contains("Change-Id:"));
            assertFalse(line.contains(
                "Change-Id: I" + ObjectId.zeroId().getName()));

            commit = git.commit().setMessage(
                messageHeader + changeIdTemplate + messageFooter)
                .setInsertChangeId(false).call();
            chars = commit.getFullMessage().getBytes(UTF_8);
            lineStart = 0;
            lineEnd = 0;
            for (int i = 0; i < 4; i++) {
                lineStart = RawParseUtils.nextLF(chars
            }
            lineEnd = RawParseUtils.nextLF(chars

            line = RawParseUtils.decode(chars

            assertTrue(commit.getFullMessage().contains(
                "Change-Id: I" + ObjectId.zeroId().getName()));
        }
    }
