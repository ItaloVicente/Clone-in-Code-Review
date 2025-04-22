				});
				updated = update.call();
				SubMonitor refreshMonitor = progress.newChild(1)
						.setWorkRemaining(updated.size());
				for (String path3 : updated) {
					Repository subRepo = SubmoduleWalk
							.getSubmoduleRepository(repository, path3);
					if (subRepo != null) {
						ProjectUtil.refreshValidProjects(
								ProjectUtil.getValidOpenProjects(subRepo),
								refreshMonitor.newChild(1));
					} else {
						refreshMonitor.worked(1);
