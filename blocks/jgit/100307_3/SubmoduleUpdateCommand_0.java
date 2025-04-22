				} else if (this.fetch) {
					if (callback != null) {
						callback.cloningSubmodule(generator.getPath());
					}
					FetchCommand fetchCommand = Git.wrap(submoduleRepo).fetch();
					if (monitor != null)
						fetchCommand.setProgressMonitor(monitor);
					fetchCommand.call();
