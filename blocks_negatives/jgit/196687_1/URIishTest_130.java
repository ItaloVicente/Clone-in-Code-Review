		String[][] tests = {
						"%2$s", "%3$s", "%4$s", "%5$s", "%6$s" },
						null, "%4$s", "%5$s", "%6$s" },
						"%4$s", null, "%6$s" },
						null, "%6$s" }, };
		String[] schemes = new String[] { "ssh", "ssh+git", "http", "https" };
		String[] users = new String[] { "me", "l usr\\example.com",
				"lusr\\example" };
		String[] passes = new String[] { "wtf", };
		String[] hosts = new String[] { "example.com", "1.2.3.4", "[::1]" };
		String[] ports = new String[] { "1234", "80" };
		String[] paths = new String[] { "/", "/abc", "D:/x", "D:\\x" };
		for (String[] test : tests) {
			String fmt = test[0];
			for (String scheme : schemes) {
				for (String user : users) {
					for (String pass : passes) {
						for (String host : hosts) {
							for (String port : ports) {
								for (String path : paths) {
									String url = String.format(fmt, scheme,
											user, pass, host, port, path);
									String[] expect = new String[test.length];
									for (int i = 1; i < expect.length; ++i)
										if (test[i] != null)
											expect[i] = String.format(test[i],
													scheme, user, pass, host,
													port, path);
									URIish urIish = new URIish(url);
									assertEquals(url, expect[1],
											urIish.getScheme());
									assertEquals(url, expect[2],
											urIish.getUser());
								}
							}
						}
					}
				}
			}
		}
