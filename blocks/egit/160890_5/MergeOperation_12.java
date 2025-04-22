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
							Activator.PLUGIN_ID, mergeResult.toString()));
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
