        try {
            final ObjectId id = git.getRef(branch).getObjectId();
            final Spliterator<RevCommit> log = git._log().add(id).call().spliterator();
            return stream(log,
                          false)
                    .filter((elem) -> elem.getName().equals(startCommitString))
                    .findFirst().orElseThrow(() -> new GitException("Commit is not present at branch " + branch));
        } catch (GitAPIException | MissingObjectException | IncorrectObjectTypeException e) {
            throw new GitException("A problem occurred when trying to get commit list",
                                   e);
        }
    }
