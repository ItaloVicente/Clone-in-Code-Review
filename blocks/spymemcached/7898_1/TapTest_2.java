	public void testTapDump() throws Exception {
		TapClient tc = new TapClient(AddrUtil.getAddresses("10.2.1.58:11210"));
		client.shutdown();
		client = new MemcachedClient(new BinaryConnectionFactory(), Arrays.asList(new InetSocketAddress("10.2.1.58", 11211)));

		HashMap<String, Boolean> items = new HashMap<String, Boolean>();
		for (int i = 0; i < 25; i++) {
			client.set("key" + i, 0, "value" + i).get();
			items.put("key" + i + ",value" + i, new Boolean(false));
		}

		tc.tapDump(null);

		long st = System.currentTimeMillis();
		int count = 0;
		while(tc.hasMoreMessages()) {
			if ((System.currentTimeMillis() - st) > TAP_DUMP_TIMEOUT) {
				fail("Tap dump took too long");
			}
			ResponseMessage m;
			if ((m = tc.getNextMessage()) != null) {
				String key = m.getKey() + "," + new String(m.getValue());
				if (items.containsKey(key)) {
					items.put(key, new Boolean(true));
					count++;
					System.out.println(key + " " + count);
					
				} else {
					fail();
				}
			}
		}
		checkTapKeys(items);
		assertTrue(client.flush().get().booleanValue());
	}

