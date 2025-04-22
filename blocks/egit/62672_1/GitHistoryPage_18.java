				if (repo != null) {
					File file = AdapterUtils.adapt(o, File.class);
					if (file == null) {
						input = new HistoryPageInput(repo);
					} else {
						input = new HistoryPageInput(repo, new File[] { file });
					}
				}
