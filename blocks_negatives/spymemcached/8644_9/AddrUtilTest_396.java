	        Set<String> validLocalhostNames=new HashSet<String>();
		validLocalhostNames.add("localhost");
		validLocalhostNames.add("ip6-localhost");
		validLocalhostNames.add("localhost6.localdomain6");
		assert(validLocalhostNames.contains(addrs.get(0).getHostName()));
		assertEquals(80, addrs.get(0).getPort());
	}
