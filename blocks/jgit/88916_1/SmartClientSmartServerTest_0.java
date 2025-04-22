	@Test(expected = TransportException.class)
	public void testInitialClone_RedirectSmallRedirectForbidden()
			throws Exception {
		Repository dst = createBareRepository();
		Config c = dst.getConfig();
		c.setEnum(ConfigConstants.CONFIG_HTTP_SECTION
				ConfigConstants.CONFIG_KEY_FOLLOWREDIRECTS
				HttpRedirectEnum.FALSE);
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}
	}

