		while(tc.hasMoreMessages()) {
			ResponseMessage m;
			if ((m = tc.getNextMessage()) != null) {
				String key = m.getKey() + ", + new String(m.getValue());
-				if (items.containsKey(key)) {
-					items.put(key, new Boolean(true));
-				} else {
-					fail();
+			while(tc.hasMoreMessages()) {
+				ResponseMessage m;
+				if ((m = tc.getNextMessage()) != null) {
+					String key = m.getKey() + , + new String(m.getValue());
+					if (items.containsKey(key)) {
+						items.put(key, new Boolean(true));
+					} else {
+						fail();
+					}
 				}
 			}
+			checkTapKeys(items);
+			assertTrue(client.flush().get().booleanValue());
 		}
-		checkTapKeys(items);
-		assertTrue(client.flush().get().booleanValue());
 	}
 
 	public void testTapBucketDoesNotExist() throws Exception {
-		TapClient client = new TapClient(Arrays.asList(new URI(http://localhost:8091/pools")),
					"abucket", "abucket", "apassword");

		try {
			client.tapBackfill(null, 5, TimeUnit.SECONDS);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
			return;
