
    @Test
    public void shouldAllowToResetAuthenticator() {
        Authenticator auth1 = new PasswordAuthenticator();
        Authenticator auth2 = new PasswordAuthenticator();

        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .authenticator(auth1)
                .build();

        Assertions.assertThat(env.authenticator()).isSameAs(auth1);

        env.authenticator(auth2);

        Assertions.assertThat(env.authenticator())
                .isNotSameAs(auth1)
                .isSameAs(auth2);
    }
