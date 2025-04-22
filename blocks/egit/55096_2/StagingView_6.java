		final boolean gerritMode = ResourcePropertyTester
				.hasGerritConfiguration(currentRepository);
		commitOperation.setComputeChangeId(gerritMode);

		PushMode pushMode = null;
		if (pushUpstream) {
			pushMode = gerritMode ? PushMode.GERRIT : PushMode.UPSTREAM;
		}
