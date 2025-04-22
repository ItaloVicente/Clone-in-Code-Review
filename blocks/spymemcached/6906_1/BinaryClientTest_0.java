	
	public void testTouchTimeout() throws Exception {
		if (isMembase()) {
			assertNull(client.get("touchkey"));
			assert client.set("touchkey", 2, "touchvalue").get().booleanValue();
			Thread.sleep(1000);
			assert client.touch("touchkey", 3).equals("touchvalue");
			Thread.sleep(2000);
			assert client.get("touchkey").equals("touchvalue");
			Thread.sleep(1100);
			assertFalse(client.touch("touchkey", 3).get().booleanValue());
		}
	}
