				GitSynchronizeDataSet gsds = (GitSynchronizeDataSet) getConfiguration()
						.getProperty(SYNCHRONIZATION_DATA);
				GitSynchronizeData gsd = gsds.iterator().next();

				String remoteName = gsd.getSrcRemoteName();
				if (remoteName == null)
					return;

				RemoteConfig rc;
				try {
					rc = new RemoteConfig(gsd.getRepository().getConfig(),
							remoteName);
					PushOperationUI push = new PushOperationUI(gsd.getRepository(),
							rc, timeout, false);
					push.setCredentialsProvider(new EGitCredentialsProvider());
					push.execute(monitor);
				} catch (URISyntaxException e) {
					throw new InvocationTargetException(e);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				}
