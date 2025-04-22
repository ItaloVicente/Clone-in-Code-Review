				repo = Adapters.adapt(o, Repository.class);
				if (repo != null) {
					File file = Adapters.adapt(o, File.class);
					if (file == null) {
						input = new HistoryPageInput(repo);
					} else {
						input = new HistoryPageInput(repo, new File[] { file });
