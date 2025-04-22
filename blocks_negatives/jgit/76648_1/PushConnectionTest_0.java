		Transport tn = testProtocol.open(uri, client, "server");
		try {
			PushConnection connection = tn.openPush();
			try {
				connection.push(NullProgressMonitor.INSTANCE, updates);
			} finally {
				connection.close();
			}
		} finally {
			tn.close();
