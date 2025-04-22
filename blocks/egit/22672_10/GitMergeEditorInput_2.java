			RevCommit ancestorCommit = getCommonAncestor(rw, rightCommit,
					headCommit);

			checkCanceled(monitor);

			setLabels(repository, rightCommit, headCommit, ancestorCommit);

			final ICompareInput input = prepareCompareInput(repository,
					filterPaths, monitor);
			if (input != null)
				return input;

			checkCanceled(monitor);
			return buildDiffContainer(repository, headCommit, ancestorCommit,
					filterPaths, rw, monitor);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		} finally {
			if (rw != null)
				rw.dispose();
			monitor.done();
		}
	}

	private void checkCanceled(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled())
			throw new InterruptedException();
	}
