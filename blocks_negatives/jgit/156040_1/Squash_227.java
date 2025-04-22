        final CommitBuilder commitBuilder = new CommitBuilder();
        commitBuilder.setParentId(parent);
        commitBuilder.setTreeId(latestCommit.getTree().getId());
        commitBuilder.setMessage(squashedCommitMessage);
        commitBuilder.setAuthor(startCommit.getAuthorIdent());
        commitBuilder.setCommitter(startCommit.getAuthorIdent());
