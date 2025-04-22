    private Set<String> cleanPath(String path
        int mode;
        String tail;
        File curFile = new File(repo.getWorkTree()
        Boolean isGitRepo = (curFile.isDirectory() && new File(curFile
        if (recurse || isGitRepo) {
            mode = FileUtils.RECURSIVE;
        } else {
            mode = FileUtils.NONE;
            tail = "";
        }

        if (isGitRepo) {
            if (force) {
                if (!dryRun) {
                    FileUtils.delete(curFile
                }
                inFiles.add(path + tail);
            }
        } else {
            if (!dryRun) {
                FileUtils.delete(curFile
            }
            inFiles.add(path + tail);
        }

        return inFiles;
    }

