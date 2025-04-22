  protected boolean mergeTreeWalk(TreeWalk treeWalk
      throws IOException {
    boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
    boolean hasAttributeNodeProvider = treeWalk
        .getAttributesNodeProvider() != null;
    while (treeWalk.next()) {
      Attributes[] attributes = {NO_ATTRIBUTES
          NO_ATTRIBUTES};
      if (hasAttributeNodeProvider) {
        attributes[T_BASE] = treeWalk.getAttributes(T_BASE);
        attributes[T_OURS] = treeWalk.getAttributes(T_OURS);
        attributes[T_THEIRS] = treeWalk.getAttributes(T_THEIRS);
      }
      if (!processEntry(
          treeWalk.getTree(T_BASE
          treeWalk.getTree(T_OURS
          treeWalk.getTree(T_THEIRS
          treeWalk.getTree(T_INDEX
          hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
              WorkingTreeIterator.class) : null
          ignoreConflicts
        ioHandler.cleanUpChanges();
        return false;
      }
      if (treeWalk.isSubtree() && enterSubtree) {
        treeWalk.enterSubtree();
      }
    }
    return true;
  }
