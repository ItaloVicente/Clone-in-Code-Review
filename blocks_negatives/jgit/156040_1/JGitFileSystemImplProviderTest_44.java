    private void checkAmbiguousFS(String fsOriginalName,
                                  String... ambiguousFsName) throws IOException {
        provider.newFileSystem(URI.create(fsOriginalName),
                               EMPTY_ENV);
        try {
            for (String fsName : ambiguousFsName) {
                provider.newFileSystem(URI.create(fsName),
                                       EMPTY_ENV);
            }
            fail("ambiguous fs");
        } catch (AmbiguousFileSystemNameException e) {
        }
    }
