					SubmoduleUpdateCommand update = git.submoduleUpdate();
					for (String path : paths)
						update.addPath(path);
					update.setProgressMonitor(new EclipseGitProgressTransformer(
							progress.newChild(2)));
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						update.setStrategy(strategy);
