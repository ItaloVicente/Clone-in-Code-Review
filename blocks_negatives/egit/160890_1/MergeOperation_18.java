		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor mymonitor) throws CoreException {
				IProject[] validProjects = ProjectUtil.getValidOpenProjects(repository);
				SubMonitor progress = SubMonitor.convert(mymonitor, NLS.bind(
						CoreText.MergeOperation_ProgressMerge, refName), 3);
				try (Git git = new Git(repository)) {
					progress.worked(1);
					MergeCommand merge = git.merge().setProgressMonitor(
							new EclipseGitProgressTransformer(
									progress.newChild(1)));
					Ref ref = repository.findRef(refName);
					if (ref != null) {
						merge.include(ref);
					} else {
						merge.include(ObjectId.fromString(refName));
					}
					if (fastForwardMode != null) {
						merge.setFastForward(fastForwardMode);
					}
					if (commit != null) {
						merge.setCommit(commit.booleanValue());
					}
					if (squash != null) {
						merge.setSquash(squash.booleanValue());
					}
					if (mergeStrategy != null) {
						merge.setStrategy(mergeStrategy);
					}
					if (message != null) {
						merge.setMessage(message);
					}
					if (repository.getConfig().getBoolean(
							ConfigConstants.CONFIG_GERRIT_SECTION,
							ConfigConstants.CONFIG_KEY_CREATECHANGEID, false)) {
						merge.setInsertChangeId(true);
					}
					mergeResult = merge.call();
					if (MergeResult.MergeStatus.NOT_SUPPORTED
							.equals(mergeResult.getMergeStatus())) {
						throw new TeamException(new Status(IStatus.INFO,
								Activator.getPluginId(),
								mergeResult.toString()));
					}
				} catch (IOException e) {
					throw new TeamException(
							CoreText.MergeOperation_InternalError, e);
				} catch (NoHeadException e) {
					throw new TeamException(
							CoreText.MergeOperation_MergeFailedNoHead, e);
				} catch (ConcurrentRefUpdateException e) {
					throw new TeamException(
							CoreText.MergeOperation_MergeFailedRefUpdate, e);
				} catch (CheckoutConflictException e) {
					mergeResult = new MergeResult(e.getConflictingPaths());
					return;
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
					ProjectUtil.refreshValidProjects(validProjects,
							progress.newChild(1));
