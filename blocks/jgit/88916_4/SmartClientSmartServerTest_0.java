	@Test
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
		} catch (TransportException te) {
			Throwable cause = te.getCause();
			assertTrue("expected RedirectForbiddenException"
					&& cause instanceof RedirectForbiddenException);
			return;
		}
		fail("expected RedirectForbiddenException as cause of a TransportException");
	}

