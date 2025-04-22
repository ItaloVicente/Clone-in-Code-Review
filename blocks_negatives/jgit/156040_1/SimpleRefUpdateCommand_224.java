    public void execute() throws IOException, ConcurrentRefUpdateException {
        final ObjectId headId = git.getLastCommit(Constants.R_HEADS + name);
        final RefUpdate ru = git.getRepository().updateRef(Constants.R_HEADS + name);
        if (headId == null) {
            ru.setExpectedOldObjectId(ObjectId.zeroId());
        } else {
            ru.setExpectedOldObjectId(headId);
        }
        ru.setNewObjectId(commit.getId());
        ru.setRefLogMessage(commit.getShortMessage(),
                            false);
        forceUpdate(ru,
                    commit.getId());
    }
