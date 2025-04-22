                        File curDir = new File(repo.getWorkTree()
                        if (!dryRun) {
                            if (RepositoryCache.FileKey.isGitRepository(new File(curDir
                                if (force) {
                                    FileUtils.delete(curDir
                                }
                            } else {
                                FileUtils.delete(curDir
                            }
                        } else {
                            if (RepositoryCache.FileKey.isGitRepository(new File(curDir
                                if (force) {
                                }
                            } else {
                            }
                        }
