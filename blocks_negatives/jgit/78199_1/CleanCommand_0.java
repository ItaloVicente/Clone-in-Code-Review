                        if (!dryRun) {
                            if (RepositoryCache.FileKey.isGitRepository(new File(curDir, DOT_GIT), fs)) {
                                if (force) {
                                    FileUtils.delete(curDir, FileUtils.RECURSIVE);
                                }
                            } else {
                                FileUtils.delete(curDir, FileUtils.RECURSIVE);
