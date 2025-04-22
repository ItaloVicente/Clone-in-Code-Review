  private File writeMergedFile(TemporaryBuffer rawMerged
      Attributes attributes)
      throws IOException {
    File workTree = nonNullRepo().getWorkTree();
    FS fs = nonNullRepo().getFS();
    File of = new File(workTree
    File parentFolder = of.getParentFile();
    if (!fs.exists(parentFolder)) {
      parentFolder.mkdirs();
    }
    StreamLoader contentLoader = ioHandler.createStreamLoader(rawMerged::openInputStream
        rawMerged.length());
    ioHandler.updateFileWithContent(contentLoader
        attributes
    return of;
  }

  private TemporaryBuffer doMerge(MergeResult<RawText> result)
      throws IOException {
    TemporaryBuffer.LocalFile buf = new TemporaryBuffer.LocalFile(
        db != null ? nonNullRepo().getDirectory() : null
    boolean success = false;
    try {
      new MergeFormatter().formatMerge(buf
          Arrays.asList(commitNames)
      buf.close();
      success = true;
    } finally {
      if (!success) {
        buf.destroy();
      }
    }
    return buf;
  }

