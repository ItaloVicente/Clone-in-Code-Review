        List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
        List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();

        assertThat(ciphersReaded).hasSize(5);
        checkCiphersName(ciphersReaded);

        assertThat(macsReaded).hasSize(6);
        checkMacsName(macsReaded);

        assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);

        sshService.stop();

        assertFalse(sshService.isRunning());
    }

    private void checkCiphersName(List<String> ciphersReaded) {
        for (String cipher : ciphersReaded) {
            assertThat(BuiltinCiphers.fromFactoryName(cipher)).isNotNull();
        }
    }
