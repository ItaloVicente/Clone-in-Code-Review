
    @Test(expected = SSLException.class)
    public void shouldFailWithSSLOverride() {
        CoreEnvironment environment = mock(CoreEnvironment.class);
        when(environment.sslKeystoreFile()).thenReturn(this.getClass().getResource("keystore.jks").getPath());
        when(environment.sslKeystorePassword()).thenReturn("keystore");

        SSLEngineFactory factory = new SSLEngineFactory(environment, "SSLv3");
        factory.get();
    }
