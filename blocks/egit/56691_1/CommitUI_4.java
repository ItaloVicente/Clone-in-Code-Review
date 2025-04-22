
		final boolean gerritMode = ResourcePropertyTester
				.hasGerritConfiguration(repo);

		PushMode pushMode = null;
		if (commitDialog.isPushRequested()) {
			pushMode = gerritMode ? PushMode.GERRIT : PushMode.UPSTREAM;
		}

		commitOperation.setComputeChangeId(gerritMode);
