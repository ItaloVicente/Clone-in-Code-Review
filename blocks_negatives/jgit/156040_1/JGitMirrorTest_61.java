    public static final String TARGET_GIT = "test/target.git";
    private static Logger logger = LoggerFactory.getLogger(JGitMirrorTest.class);

    @Test
    public void testToHTTPMirrorSuccess() throws IOException, GitAPIException {
        final File parentFolder = createTempDirectory();
        final File directory = new File(parentFolder,
                                        TARGET_GIT);
        new Clone(directory,
                  ORIGIN,
                  true,
                  null,
                  CredentialsProvider.getDefault(),
                  null,
                  null).execute();

        final Git cloned = Git.open(directory);

        assertThat(cloned).isNotNull();

        assertThat(cloned.getRepository().getAllRefs()).is(new Condition<Map<String, Ref>>() {
            @Override
            public boolean matches(final Map<String, Ref> refs) {
                final boolean hasMasterRef = refs.get("refs/heads/master") != null;
                final boolean hasNewWebsiteRef = refs.get("refs/heads/new-website") != null;
                final boolean hasRemoteOriginMaster = refs.get("refs/remotes/origin/master") != null;
                final boolean hasRemoteOriginNewWebSite = refs.get("refs/remotes/origin/master") != null;

                return hasMasterRef && hasNewWebsiteRef && hasRemoteOriginMaster & hasRemoteOriginNewWebSite;
            }
        });

        URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
        assertThat(remoteUrl).isEqualTo(ORIGIN);
    }

    @Test
    public void testToHTTPUnmirrorSuccess() throws IOException, GitAPIException {
        final File parentFolder = createTempDirectory();
        final File directory = new File(parentFolder,
                                        TARGET_GIT);
        new Clone(directory,
                  ORIGIN,
                  false,
                  null,
                  CredentialsProvider.getDefault(),
                  null,
                  null).execute();

        final Git cloned = Git.open(directory);

        assertThat(cloned).isNotNull();

        assertThat(cloned.getRepository().getAllRefs()).is(new Condition<Map<String, Ref>>() {
            @Override
            public boolean matches(final Map<String, Ref> refs) {
                final boolean hasMasterRef = refs.get("refs/heads/master") != null;
                final boolean hasNewWebsiteRef = refs.get("refs/heads/new-website") != null;
                final boolean hasRemoteOriginMaster = refs.get("refs/remotes/origin/master") != null;
                final boolean hasRemoteOriginNewWebSite = refs.get("refs/remotes/origin/master") != null;

                return hasMasterRef && hasNewWebsiteRef && hasRemoteOriginMaster & hasRemoteOriginNewWebSite;
            }
        });

        final boolean isMirror = cloned.getRepository().getConfig().getBoolean("remote",
                                                                               "origin",
                                                                               "mirror",
                                                                               false);
        assertFalse(isMirror);

        assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");

        URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
        assertThat(remoteUrl).isEqualTo(ORIGIN);
    }

    @Test
    public void testEmptyCredentials() throws IOException, GitAPIException {
        final File parentFolder = createTempDirectory();
        final File directory = new File(parentFolder,
                                        TARGET_GIT);
        new Clone(directory,
                  ORIGIN,
                  false,
                  null,
                  null,
                  null,
                  null).execute();

        final Git cloned = Git.open(directory);

        assertThat(cloned).isNotNull();

        assertThat(new ListRefs(cloned.getRepository()).execute()).is(new Condition<List<? extends Ref>>() {
            @Override
            public boolean matches(final List<? extends Ref> refs) {
                return refs.size() > 0;
            }
        });

        assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");

        URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
        assertThat(remoteUrl).isEqualTo(ORIGIN);
    }

    @Test
    public void testBadUrl() throws IOException, GitAPIException {
        final File parentFolder = createTempDirectory();
        final File directory = new File(parentFolder,
                                        TARGET_GIT);
        try {
            new Clone(directory,
                      ORIGIN + "sssss",
                      false,
                      null,
                      CredentialsProvider.getDefault(),
                      null,
                      null).execute();
            fail("If got here the test is wrong because the ORIGIN does no exist");
        } catch (Clone.CloneException ex) {
            assertThat(ex).isNotNull();
            logger.info(ex.getMessage(),
                        ex);
        }
    }
