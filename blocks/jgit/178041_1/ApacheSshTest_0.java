	}

	private SshServer createProxy(String user
			SshdSocketAddress[] report) throws Exception {
		SshServer proxy = createServer(user
		proxy.setForwardingFilter(new StaticDecisionForwardingFilter(true) {

			@Override
			protected boolean checkAcceptance(String request
					SshdSocketAddress target) {
				report[0] = target;
				return super.checkAcceptance(request
			}
		});
		proxy.start();
		registerServer(proxy);
