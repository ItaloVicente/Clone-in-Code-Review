	public void testTouchTimeout() throws Exception {
		if (TestConfig.isMembase()) {
			assertNull(client.get("touchkey"));
			assert client.set("touchkey", 1, "touchvalue").get().booleanValue();
			assert client.touch("touchkey", 2).get().booleanValue();
			Thread.sleep(1300);
			assert client.get("touchkey").equals("touchvalue");
			Thread.sleep(2000);
			assertFalse(client.touch("touchkey", 3).get().booleanValue());
		}
	}
