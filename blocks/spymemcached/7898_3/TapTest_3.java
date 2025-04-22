	public void testTapDump() throws Exception {
		if (TestConfig.isMembase()) {
			TapClient tc = new TapClient(AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11210"));

			HashMap<String, Boolean> items = new HashMap<String, Boolean>();
			for (int i = 0; i < 25; i++) {
				client.set("key" + i, 0, "value" + i).get();
				items.put("key" + i + ",value" + i, new Boolean(false));
			}
			tc.tapDump(null);

			long st = System.currentTimeMillis();
			while(tc.hasMoreMessages()) {
				if ((System.currentTimeMillis() - st) > TAP_DUMP_TIMEOUT) {
					fail("Tap dump took too long");
				}
				ResponseMessage m;
				if ((m = tc.getNextMessage()) != null) {
					String key = m.getKey() + "," + new String(m.getValue());
					if (items.containsKey(key)) {
						items.put(key, new Boolean(true));
					} else {
						fail();
					}
				}
			}
			checkTapKeys(items);
			assertTrue(client.flush().get().booleanValue());
		}
	}

