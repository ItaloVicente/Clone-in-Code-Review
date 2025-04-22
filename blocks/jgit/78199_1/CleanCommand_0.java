    private Set<String> cleanPath(String path
        int mode;
        String tail;
        if (recurse) {
            mode = FileUtils.RECURSIVE;
        } else {
            mode = FileUtils.NONE;
            tail = "";
        }

        if (!dryRun) {
            FileUtils.delete(new File(repo.getWorkTree()
        }

        inFiles.add(path + tail);
        return inFiles;
    }

