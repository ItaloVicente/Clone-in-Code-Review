    final String bucketName, String password) throws IOException {
    initialize(baseList, bucketName, password);
  }

  private void initialize(List<URI> baseList, String bucket, String password) {
    potentiallyRandomizeNodeList(baseList);

