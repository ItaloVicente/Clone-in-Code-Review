		final boolean gerritMode = addChangeIdAction.isChecked();
		commitOperation.setComputeChangeId(gerritMode);

		PushMode pushMode = null;
		if (pushUpstream) {
			pushMode = gerritMode ? PushMode.GERRIT : PushMode.UPSTREAM;
		}
