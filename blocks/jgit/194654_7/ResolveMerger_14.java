  @Override
  public ObjectId getResultTreeId() {
    return (resultTree == null) ? null : resultTree.toObjectId();
  }

  public void setCommitNames(String[] commitNames) {
    this.commitNames = commitNames;
  }

