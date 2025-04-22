        verify(node1, node2, node3);
*/
    }
    public void testGetConfig() {
        ConfigFactory configFactory = new DefaultConfigFactory();
        Config config = configFactory.create(configInEnvelope);
	config.getServersCount();
	List<String> servers = config.getServers();
	System.out.println(servers);
    }
