        sshService.stop();

        assertFalse(sshService.isRunning());
    }

    @Test
    public void testWithWrongCiphersAndMacs() throws Exception {
        final GitSSHService sshService = new GitSSHService();
        final File certDir = createTempDirectory();
