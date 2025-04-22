
	@Test
	public void testALot() throws URISyntaxException {
		String[][] tests = {
						"%2$s"
						null
						"%4$s"
						null
		String[] schemes = new String[] { "ssh"
		String[] users = new String[] { "me"
				"lusr\\example" };
		String[] passes = new String[] { "wtf"
		String[] hosts = new String[] { "example.com"
		String[] ports = new String[] { "1234"
		String[] paths = new String[] { "/"
		for (String[] test : tests) {
			String fmt = test[0];
			for (String scheme : schemes) {
				for (String user : users) {
					for (String pass : passes) {
						for (String host : hosts) {
							for (String port : ports) {
								for (String path : paths) {
									String url = String.format(fmt
											user
									String[] expect = new String[test.length];
									for (int i = 1; i < expect.length; ++i)
										if (test[i] != null)
											expect[i] = String.format(test[i]
													scheme
													port
									URIish urIish = new URIish(url);
									assertEquals(url
											urIish.getScheme());
									assertEquals(url
											urIish.getUser());
								}
							}
						}
					}
				}
			}
		}
	}
