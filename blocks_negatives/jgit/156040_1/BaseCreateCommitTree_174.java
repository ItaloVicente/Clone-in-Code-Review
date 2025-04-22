    BaseCreateCommitTree(final Git git,
                         final ObjectId headId,
                         final ObjectInserter inserter,
                         final T commitContent) {
        this.git = git;
        this.headId = headId;
        this.odi = inserter;
        this.commitContent = commitContent;
    }
