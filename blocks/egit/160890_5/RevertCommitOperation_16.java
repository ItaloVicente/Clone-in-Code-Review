				newHead = command.call();
				reverted = command.getRevertedRefs();
				result = command.getFailingResult();
				progress.worked(1);
				ProjectUtil.refreshValidProjects(
						ProjectUtil.getValidOpenProjects(repo),
						progress.newChild(1));
			} catch (GitAPIException e) {
				throw new TeamException(e.getLocalizedMessage(),
						e.getCause());
