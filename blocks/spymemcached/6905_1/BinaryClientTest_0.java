
	public void testGATTimeout() throws Exception {
		if (isMembase()) {
			assertNull(client.get("gatkey"));
			assert client.set("gatkey", 2, "gatvalue").get().booleanValue();
			Thread.sleep(1000);
			assert client.getAndTouch("gatkey", 3).equals("gatvalue");
			Thread.sleep(2000);
			assert client.get("gatkey").equals("gatvalue");
			Thread.sleep(1100);
			assertFalse(client.getAndTouch("gatkey", 3).equals("gatvalue"));
		}
	}
