					monitor, openTicks));
			if (share) {
				ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
						project, gitRepositoryDir);
				connectProviderOperation
						.run(new SubProgressMonitor(monitor, 20));
			}
