            if (!allowEmpty) {
                if (only.isEmpty()) {
                    Status status = git.status().call();
                    if ((status.getAdded().size() + status.getChanged().size() + status.getRemoved().size()) == 0) {
                        throw new JGitInternalException(JGitText.get().emptyCommit);
                    }
                }
            }

