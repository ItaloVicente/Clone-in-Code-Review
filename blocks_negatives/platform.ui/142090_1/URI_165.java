    String[] mergedSegments = tailSegments;
    if (newPrefix.segmentCount() != 0)
    {
      int segmentsToKeep = newPrefix.segmentCount() - 1;
      mergedSegments = new String[segmentsToKeep + tailSegments.length];
      System.arraycopy(newPrefix.segments(), 0, mergedSegments, 0,
                       segmentsToKeep);

      if (tailSegments.length != 0)
      {
        System.arraycopy(tailSegments, 0, mergedSegments, segmentsToKeep,
                         tailSegments.length);
      }
    }

    return new URI(true, newPrefix.scheme(), newPrefix.authority(),
                   newPrefix.device(), newPrefix.hasAbsolutePath(),
                   mergedSegments, query, fragment);
  }

  private String[] getTailSegments(URI prefix)
  {
    if (!prefix.isPrefix())
    {
      throw new IllegalArgumentException("non-prefix trim");
    }

    if (!isHierarchical() ||
        !equals(scheme, prefix.scheme(), true) ||
        !equals(authority, prefix.authority()) ||
        !equals(device, prefix.device()) ||
        hasAbsolutePath() != prefix.hasAbsolutePath())
    {
      return null;
    }

    if (prefix.segmentCount() == 0) {
		return segments;
	}

    int i = 0;
    int segmentsToCompare = prefix.segmentCount() - 1;
    if (segments.length <= segmentsToCompare) {
		return null;
