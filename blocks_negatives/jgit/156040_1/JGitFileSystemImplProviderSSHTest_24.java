    private int gitSSHPort;

    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();

        gitPrefs.put(GIT_SSH_ENABLED, "true");
        gitSSHPort = findFreePort();
        gitPrefs.put(GIT_SSH_PORT,
                     String.valueOf(gitSSHPort));
        gitPrefs.put(GIT_SSH_IDLE_TIMEOUT,
                     "10001");

        return gitPrefs;
    }

    @Test
    public void testSSHPostReceiveHook() throws IOException {
        FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
            @Override
            public void execute(FileSystemHookExecutionContext context) {
                assertEquals("repo", context.getFsName());
            }
        });

        Assume.assumeFalse("UF-511",
                           System.getProperty("java.vendor").equals("IBM Corporation"));
        provider.setJAASAuthenticator(new AuthenticationService() {
            private User user;

            @Override
            public User login(String s, String s1) {
                user = new User() {
                    @Override
                    public String getIdentifier() {
                        return s;
                    }
                };
                return user;
            }

            @Override
            public boolean isLoggedIn() {
                return user != null;
            }

            @Override
            public void logout() {
                user = null;
            }

            @Override
            public User getUser() {
                return user;
            }
        });
        provider.setAuthorizer((fs, fileSystemUser) -> true);

        CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("admin",
                                                                               ""));
        assertEquals("10001",
                     provider.getGitSSHService().getProperties().get(SshServer.IDLE_TIMEOUT));

        final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo,
                                                                              new HashMap<String, Object>() {{
                                                                                  put(FileSystemHooks.ExternalUpdate.name(), hook);
                                                                              }});

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file-name.txt",
                           tempFile("temp1"));
                   }}).execute();

        JGitFileSystem clone;
                                                        new HashMap<String, Object>() {{
                                                            put("init",
                                                                "true");
                                                            put("origin",
                                                        }});

        assertNotNull(clone);


        ArgumentCaptor<FileSystemHookExecutionContext> captor = ArgumentCaptor.forClass(FileSystemHookExecutionContext.class);

        verify(hook).execute(captor.capture());

        Assertions.assertThat(captor.getValue())
                .isNotNull()
                .hasFieldOrPropertyWithValue("fsName", "repo");
    }
