    @Test
    public void testGetPath() throws IOException {

        provider.newFileSystem(newRepo,
                               EMPTY_ENV);


        AssertionsForClassTypes.assertThat(path).isNotNull();
        assertThat(path.getRoot().toString()).isEqualTo("/");
        Path root = path.getRoot();
        Path path1 = root.toRealPath();
        assertThat(path.toString()).isEqualTo("/home");
