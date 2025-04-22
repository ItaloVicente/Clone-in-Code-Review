	@Test
	public void testOpenPushUseBitmaps() throws Exception {
		try (Transport transport = Transport.open(uri)) {
			try (PushConnection pushConnection = transport.openPush()) {
				assertTrue(pushConnection instanceof BasePackPushConnection);
				BasePackPushConnection basePackPushConnection = (BasePackPushConnection) pushConnection;
				assertEquals(true
			}
		}
		try (Transport transport = Transport.open(uri)) {
			transport.setPushUseBitmaps(true);
			try (PushConnection pushConnection = transport.openPush()) {
				assertTrue(pushConnection instanceof BasePackPushConnection);
				BasePackPushConnection basePackPushConnection = (BasePackPushConnection) pushConnection;
				assertEquals(true
			}
		}
		try (Transport transport = Transport.open(uri)) {
			transport.setPushUseBitmaps(false);
			try (PushConnection pushConnection = transport.openPush()) {
				assertTrue(pushConnection instanceof BasePackPushConnection);
				BasePackPushConnection basePackPushConnection = (BasePackPushConnection) pushConnection;
				assertEquals(false
			}
		}
	}

