        try (final ObjectInserter odi = repo.newObjectInserter()) {
            final RevCommit squashedCommit = git.resolveRevCommit(odi.insert(commitBuilder));
            git.refUpdate(branch,
                          squashedCommit);
        } catch (ConcurrentRefUpdateException | IOException e) {
            throw new GitException("Error on executing squash.",
                                   e);
        }
    }
