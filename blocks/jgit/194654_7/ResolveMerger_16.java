  protected boolean mergeTrees(AbstractTreeIterator baseTree
      RevTree headTree
      throws IOException {
    ioHandler =
        inCore ? InCoreSupport.initRepoIOHandlerForRemote(db
            InCoreSupport.initRepoIOHandlerForLocal(db
    dircache = ioHandler.getLockedDirCache();
    try {
      tw = new NameConflictTreeWalk(db

      tw.addTree(baseTree);
      tw.setHead(tw.addTree(headTree));
      tw.addTree(mergeTree);
      DirCacheBuildIterator buildIt = ioHandler.createDirCacheBuildIterator();
      int dciPos = tw.addTree(buildIt);
      if (workingTreeIterator != null) {
        tw.addTree(workingTreeIterator);
        workingTreeIterator.setDirCacheIterator(tw
      } else {
        tw.setFilter(TreeFilter.ANY_DIFF);
      }

      if (!mergeTreeWalk(tw
        return false;
      }

      ioHandler.finishBuilding(true);
      if (getUnmergedPaths().isEmpty() && !failed()) {
        resultTree = ioHandler.writeChangesAndCleanUp();
        List<String> failedToDelete = ioHandler.getFailedToDelete();
        for (String f : failedToDelete) {
          failingPaths.put(f
        }
        return failedToDelete.isEmpty();
      }
      resultTree = null;
      return false;
    } finally {
      ioHandler.cleanUpCache();
    }
  }

