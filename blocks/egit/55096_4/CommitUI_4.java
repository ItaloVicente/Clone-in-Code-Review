
		final boolean gerritMode = commitDialog.getCreateChangeId();

		PushMode pushMode = null;
		if (commitDialog.isPushRequested()) {
			pushMode = gerritMode ? PushMode.GERRIT : PushMode.UPSTREAM;
		}

		commitOperation.setComputeChangeId(gerritMode);
