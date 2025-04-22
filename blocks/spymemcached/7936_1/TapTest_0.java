		if (TestConfig.isMembase()) {
			TapClient tc = new TapClient(AddrUtil.getAddresses("127.0.0.1:11210"));
			tc.tapBackfill(null, 5, TimeUnit.SECONDS);
	
			HashMap<String, Boolean> items = new HashMap<String, Boolean>();
			for (int i = 0; i < 25; i++) {
				client.set("key" + i, 0, "value" + i);
				items.put("key" + i + ",value" + i, new Boolean(false));
			}
	
			while(tc.hasMoreMessages()) {
				ResponseMessage m;
				if ((m = tc.getNextMessage()) != null) {
					String key = m.getKey() + "," + new String(m.getValue());
					if (items.containsKey(key)) {
						items.put(key, new Boolean(true));
					} else {
						fail();
					}
