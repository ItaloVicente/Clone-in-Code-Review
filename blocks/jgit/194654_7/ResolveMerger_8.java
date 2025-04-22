  protected boolean processEntry(CanonicalTreeParser base
      CanonicalTreeParser ours
      DirCacheBuildIterator index
      boolean ignoreConflicts
      throws IOException {
    enterSubtree = true;
    final int modeO = tw.getRawMode(T_OURS);
    final int modeT = tw.getRawMode(T_THEIRS);
    final int modeB = tw.getRawMode(T_BASE);
    boolean gitLinkMerging = isGitLink(modeO) || isGitLink(modeT)
        || isGitLink(modeB);
    if (modeO == 0 && modeT == 0 && modeB == 0)
    {
      return true;
    }

    if (isIndexDirty()) {
      return false;
    }

    DirCacheEntry ourDce = null;

    if (index == null || index.getDirCacheEntry() == null) {
      if (nonTree(modeO)) {
        ourDce = new DirCacheEntry(tw.getRawPath());
        ourDce.setObjectId(tw.getObjectId(T_OURS));
        ourDce.setFileMode(tw.getFileMode(T_OURS));
      }
    } else {
      ourDce = index.getDirCacheEntry();
    }

    if (nonTree(modeO) && nonTree(modeT) && tw.idEqual(T_OURS
      if (modeO == modeT) {
        keep(ourDce);
        return true;
      }
      int newMode = mergeFileModes(modeB
      if (newMode != FileMode.MISSING.getBits()) {
        if (newMode == modeO) {
          keep(ourDce);
        } else {
          if (isWorktreeDirty(work
            return false;
          }
          DirCacheEntry e = add(tw.getRawPath()
              DirCacheEntry.STAGE_0
          addToCheckout(tw.getPathString()
        }
        return true;
      }
      if (!ignoreConflicts) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        unmergedPaths.add(tw.getPathString());
        mergeResults.put(tw.getPathString()
            new MergeResult<>(Collections.emptyList()));
      }
      return true;
    }

    if (modeB == modeT && tw.idEqual(T_BASE
      if (ourDce != null) {
        keep(ourDce);
      }
      return true;
    }

    if (modeB == modeO && tw.idEqual(T_BASE

      if (isWorktreeDirty(work
        return false;
      }
      if (nonTree(modeT)) {
        DirCacheEntry e = add(tw.getRawPath()
            DirCacheEntry.STAGE_0
        if (e != null) {
          addToCheckout(tw.getPathString()
        }
        return true;
      }
      if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) {
        return true;
      }
      if (modeT != 0 && modeT == modeB) {
        return true;
      }
      addDeletion(tw.getPathString()
      return true;
    }

    if (tw.isSubtree()) {
      if (nonTree(modeO) != nonTree(modeT)) {
        if (ignoreConflicts) {
          enterSubtree = false;
          return true;
        }
        if (nonTree(modeB)) {
          add(tw.getRawPath()
        }
        if (nonTree(modeO)) {
          add(tw.getRawPath()
        }
        if (nonTree(modeT)) {
          add(tw.getRawPath()
        }
        unmergedPaths.add(tw.getPathString());
        enterSubtree = false;
        return true;
      }

      if (!nonTree(modeO)) {
        return true;
      }

    }

    if (nonTree(modeO) && nonTree(modeT)) {
      boolean worktreeDirty = isWorktreeDirty(work
      if (!attributes[T_OURS].canBeContentMerged() && worktreeDirty) {
        return false;
      }

      if (gitLinkMerging && ignoreConflicts) {
        add(tw.getRawPath()
        return true;
      } else if (gitLinkMerging) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
            base
        result.setContainsConflicts(true);
        mergeResults.put(tw.getPathString()
        unmergedPaths.add(tw.getPathString());
        return true;
      } else if (!attributes[T_OURS].canBeContentMerged()) {
        switch (getContentMergeStrategy()) {
          case OURS:
            keep(ourDce);
            return true;
          case THEIRS:
            DirCacheEntry theirEntry = add(tw.getRawPath()
                DirCacheEntry.STAGE_0
            addToCheckout(tw.getPathString()
            return true;
          default:
            break;
        }
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()

        unmergedPaths.add(tw.getPathString());
        return true;
      }

      if (worktreeDirty) {
        return false;
      }
