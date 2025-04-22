      try {
        result = contentMerge(base
            getContentMergeStrategy());
      } catch (BinaryBlobException e) {
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
            result = new MergeResult<>(Collections.emptyList());
            result.setContainsConflicts(true);
            break;
        }
      }
      if (ignoreConflicts) {
        result.setContainsConflicts(false);
      }
      updateIndex(base
      String currentPath = tw.getPathString();
      if (result.containsConflicts() && !ignoreConflicts) {
        unmergedPaths.add(currentPath);
        ioHandler.markAsModified(currentPath);
      }
      addToCheckout(currentPath
    } else if (modeO != modeT) {
      if (((modeO != 0 && !tw.idEqual(T_BASE
          .idEqual(T_BASE
        if (gitLinkMerging && ignoreConflicts) {
          add(tw.getRawPath()
        } else if (gitLinkMerging) {
          add(tw.getRawPath()
          add(tw.getRawPath()
          add(tw.getRawPath()
          MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
              base
          result.setContainsConflicts(true);
          mergeResults.put(tw.getPathString()
          unmergedPaths.add(tw.getPathString());
        } else {
          MergeResult<RawText> result;
          try {
            result = contentMerge(base
                ContentMergeStrategy.CONFLICT);
          } catch (BinaryBlobException e) {
            result = new MergeResult<>(Collections.emptyList());
            result.setContainsConflicts(true);
          }
          if (ignoreConflicts) {
            result.setContainsConflicts(false);
            updateIndex(base
                attributes[T_OURS]);
          } else {
            add(tw.getRawPath()
                0);
            add(tw.getRawPath()
                0);
            DirCacheEntry e = add(tw.getRawPath()
                DirCacheEntry.STAGE_3

            if (modeO == 0) {
              if (isWorktreeDirty(work
                return false;
              }
              if (nonTree(modeT) && e != null) {
                addToCheckout(tw.getPathString()
                    attributes);
              }
            }

            unmergedPaths.add(tw.getPathString());

            mergeResults.put(tw.getPathString()
          }
        }
      }
    }
    return true;
  }

  private static MergeResult<SubmoduleConflict> createGitLinksMergeResult(
      CanonicalTreeParser base
      CanonicalTreeParser theirs) {
    return new MergeResult<>(Arrays.asList(
        new SubmoduleConflict(
            base == null ? null : base.getEntryObjectId())
        new SubmoduleConflict(
            ours == null ? null : ours.getEntryObjectId())
        new SubmoduleConflict(
            theirs == null ? null : theirs.getEntryObjectId())));
  }

