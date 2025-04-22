		final String[] results = new String[2];
		Job verifier = new Job(testName.getMethodName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				waitForFinalization(5000);
				Repository[] repositories = org.eclipse.egit.core.Activator
						.getDefault().getRepositoryCache().getAllRepositories();
				results[0] = Arrays.asList(repositories).toString();
				IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
						.getDefault().getIndexDiffCache();
				results[1] = indexDiffCache.currentCacheEntries().toString();
				return Status.OK_STATUS;
			}

		};
		verifier.setRule(new RepositoryCacheRule());
		verifier.setSystem(true);
		verifier.schedule();
		verifier.join();
		assertEquals("Expected no cached repositories", "[]", results[0]);
		assertEquals("Expected no IndexDiffCache entries", "[]", results[1]);
