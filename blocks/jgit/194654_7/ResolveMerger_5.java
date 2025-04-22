  @Override
  protected boolean mergeImpl() throws IOException {
    return mergeTrees(mergeBase()
        false);
  }


  private DirCacheEntry add(byte[] path
      Instant lastMod
    if (p != null && !p.getEntryFileMode().equals(FileMode.TREE)) {
      return ioHandler.addExistingToIndex(p.getEntryObjectId()
          p.getEntryFileMode()
          lastMod
    }
    return null;
  }

