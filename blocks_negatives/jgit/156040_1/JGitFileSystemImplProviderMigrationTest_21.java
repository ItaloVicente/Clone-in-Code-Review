    @Test
    public void testCreateANewDirectoryWithMigrationEnv() throws IOException {

        final Map<String, ?> envMigrate = new HashMap<String, Object>() {{
            put("init",
                Boolean.TRUE);
            put("migrate-from",
        }};

        final URI newUri = URI.create(newPath);
        provider.newFileSystem(newUri,
                               envMigrate);

        provider.getFileSystem(newUri);
        assertThat(new File(provider.getGitRepoContainerDir(),
                            "test/old" + ".git")).exists();
        assertThat(provider.getFileSystem(newUri)).isNotNull();
    }
