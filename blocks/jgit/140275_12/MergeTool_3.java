        for (String name : userTools.keySet()) {
                    + userTools.get(name).getCommand());
        }
        outw.println(
                "The following tools are valid
        for (String name : predefTools.keySet()) {
            if (!predefTools.get(name).isAvailable()) {
            }
        }
        outw.println(
                "environment. If run in a terminal-only session
        return;
    }

    private Map<String
            throws RevisionSyntaxException
            GitAPIException {
        Map<String
        try (Git git = new Git(db)) {
            StatusCommand statusCommand = git.status();
            if (filterPaths != null && filterPaths.size() > 0) {
                for (String path : filterPaths) {
                    statusCommand.addPath(path);
                }
            }
            org.eclipse.jgit.api.Status status = statusCommand.call();
            files = status.getConflictingStageState();
        }
        return files;
    }
