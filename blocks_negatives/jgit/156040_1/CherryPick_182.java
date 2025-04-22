                if (srcCommit.getParentCount() != 1) {
                    throw new IOException(new MultipleParentsNotAllowedException(
                            MessageFormat.format(
                                    JGitText.get().canOnlyCherryPickCommitsWithOneParent,
                                    srcCommit.name(),
                                    srcCommit.getParentCount())));
                }
