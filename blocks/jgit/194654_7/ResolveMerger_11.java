  private void updateIndex(CanonicalTreeParser base
      CanonicalTreeParser ours
      MergeResult<RawText> result
      throws IOException {
    TemporaryBuffer rawMerged = null;
    try {
      rawMerged = doMerge(result);
      File mergedFile = inCore ? null
          : writeMergedFile(rawMerged
      if (result.containsConflicts()) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        mergeResults.put(tw.getPathString()
        return;
      }

      Instant lastModified =
          mergedFile == null ? null : nonNullRepo().getFS().lastModifiedInstant(mergedFile);
      int length = mergedFile == null ? 0 : (int) mergedFile.length();
      int newMode = mergeFileModes(tw.getRawMode(0)
          tw.getRawMode(2));
      FileMode mode = newMode == FileMode.MISSING.getBits()
          ? FileMode.REGULAR_FILE : FileMode.fromBits(newMode);
      StreamLoader contentLoader = ioHandler.createStreamLoader(rawMerged::openInputStream
          rawMerged.length());
      ioHandler.insertToIndex(contentLoader
          DirCacheEntry.STAGE_0
    } finally {
      if (rawMerged != null) {
        rawMerged.destroy();
      }
    }
  }

