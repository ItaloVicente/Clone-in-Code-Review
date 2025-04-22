					monitor, openTicks));
			if (share) {
				ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
						project, gitRepositoryDir);
				connectProviderOperation
						.execute(new SubProgressMonitor(monitor, 20));
			}
