    @AfterClass
    @BeforeClass
    public static void cleanup() {
        try {
            FileUtils.delete(new File(".niogit"),
                             FileUtils.RECURSIVE);
        } catch (IOException ex) {
        }
    }

    @Test
    public void testSimpleBuilderSample() throws IOException {
        final FileSystem fs = JGitFileSystemBuilder.newFileSystem("myrepo");

        Path foo = fs.getPath("/foo");
        Files.createDirectory(foo);


        Files.write(hello, Collections.singletonList("hello world"), StandardCharsets.UTF_8);

        assertEquals("hello world", Files.readAllLines(hello).get(0));
    }
