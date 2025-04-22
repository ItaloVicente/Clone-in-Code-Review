                    if (paths.isEmpty() || paths.contains(dir)) {
                        File curDir = new File(repo.getWorkTree()
                        if (RepositoryCache.FileKey.isGitRepository(new File(curDir
                            if (force) {
                                files = cleanPath(dir
                            }
                        } else {
                            files = cleanPath(dir
                        }
                    }
