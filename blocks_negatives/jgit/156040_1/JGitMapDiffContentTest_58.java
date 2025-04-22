        final File gitSource = new File(parentFolder, "source/source.git");

        git = new CreateRepository(gitSource).execute().get();

        commit(git, MASTER_BRANCH, "Adding files into master",
               content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
               content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
               content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));
    }
