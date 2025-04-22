  @Override
  public void addClone(Operation op) {
    clones.add(op);
  }

  @Override
  public int getCloneCount() {
    return cloneCount;
  }

  @Override
  public void setCloneCount(int count) {
    cloneCount = count;
  }
