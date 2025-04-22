    @Test
    public void testHttpProxy() throws IOException {
        final String userName = "user";
        final String passw = "passwd";

        final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {{
            put("http.proxyUser",
                "user");
            put("http.proxyPassword",
                "passwd");
            put(GIT_DAEMON_ENABLED,
                "false");
            put(GIT_SSH_ENABLED,
                "false");
        }});

        final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
                                                                                InetAddress.getLocalHost(),
                                                                                8080,
                                                                                "http",
                                                                                "xxx",
                                                                                "http",
                                                                                Authenticator.RequestorType.PROXY);

        assertEquals(userName,
                     passwdAuth.getUserName());
        assertEquals(passw,
                     new String(passwdAuth.getPassword()));

        provider.dispose();
    }

    @Test
    public void testHttpsProxy() throws IOException {
        final String userName = "user";
        final String passw = "passwd";

        final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {{
            put("https.proxyUser",
                "user");
            put("https.proxyPassword",
                "passwd");
            put(GIT_DAEMON_ENABLED,
                "false");
            put(GIT_SSH_ENABLED,
                "false");
        }});

        final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
                                                                                InetAddress.getLocalHost(),
                                                                                8080,
                                                                                "https",
                                                                                "xxx",
                                                                                "https",
                                                                                Authenticator.RequestorType.PROXY);

        assertEquals(userName,
                     passwdAuth.getUserName());
        assertEquals(passw,
                     new String(passwdAuth.getPassword()));

        provider.dispose();
    }

    @Test
    public void testNoProxyInfo() throws IOException {
        final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {{
            put(GIT_DAEMON_ENABLED, "false");
            put(GIT_SSH_ENABLED, "false");
        }});

        {
            final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
                                                                                    InetAddress.getLocalHost(),
                                                                                    8080,
                                                                                    "https",
                                                                                    "xxx",
                                                                                    "https",
                                                                                    Authenticator.RequestorType.PROXY);

            assertNull(passwdAuth);
        }

        {
            final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
                                                                                    InetAddress.getLocalHost(),
                                                                                    8080,
                                                                                    "http",
                                                                                    "xxx",
                                                                                    "http",
                                                                                    Authenticator.RequestorType.PROXY);

            assertNull(passwdAuth);
        }

        provider.dispose();
    }
