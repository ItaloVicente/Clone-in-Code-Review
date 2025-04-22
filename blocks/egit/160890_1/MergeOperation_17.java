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
			} catch (IOException e1) {
				throw new TeamException(
						CoreText.MergeOperation_InternalError, e1);
			} catch (NoHeadException e2) {
				throw new TeamException(
						CoreText.MergeOperation_MergeFailedNoHead, e2);
			} catch (ConcurrentRefUpdateException e3) {
				throw new TeamException(
						CoreText.MergeOperation_MergeFailedRefUpdate, e3);
			} catch (CheckoutConflictException e4) {
				mergeResult = new MergeResult(e4.getConflictingPaths());
				return;
			} catch (GitAPIException e5) {
				throw new TeamException(e5.getLocalizedMessage(),
						e5.getCause());
			} finally {
				ProjectUtil.refreshValidProjects(validProjects,
						progress.newChild(1));
