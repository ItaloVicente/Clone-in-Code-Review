        rootURIs.removeAll(rootURIs1);

        assertThat(rootURIs).isEmpty();

        new Commit(origin.getGit(),
                   "user-branch-2",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file2UserBranch.txt",
                           tempFile("tempX"));
                   }}).execute();


        assertThat(fs2.getRootDirectories()).hasSize(3);

        for (final Path root : fs2.getRootDirectories()) {
            rootURIs.add(root.toUri().toString());
        }

        rootURIs.removeAll(rootURIs2);
