  protected void addDeletion(String path
      Attributes attributes) throws IOException {
    File file =
        isFile && !nonNullRepo().isBare() ? new File(nonNullRepo().getWorkTree()
    String smudgeCommand = tw.getSmudgeCommand(attributes);
    ioHandler.deleteFile(path
  }

