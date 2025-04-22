		SWTCommit commit = (SWTCommit) ti.getData();
		try {
			commit.parseBody();
		} catch (IOException e) {
			Activator.error("Error parsing body", e); //$NON-NLS-1$
			return;
		}
		paintCommit(commit , event.height);
