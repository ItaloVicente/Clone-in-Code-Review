						@Override
						public void checkingOut(AnyObjectId commit,
								String path) {
						}
					});
					updated = update.call();
					SubMonitor refreshMonitor = progress.newChild(1)
							.setWorkRemaining(updated.size());
					for (String path : updated) {
						Repository subRepo = SubmoduleWalk
								.getSubmoduleRepository(repository, path);
						if (subRepo != null) {
							ProjectUtil.refreshValidProjects(
									ProjectUtil.getValidOpenProjects(subRepo),
									refreshMonitor.newChild(1));
						} else {
							refreshMonitor.worked(1);
						}
