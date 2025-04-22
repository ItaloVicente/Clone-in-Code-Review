			if (repo == null) {
				repo = AdapterUtils.adapt(o, Repository.class);
				if (repo != null)
					input = new HistoryPageInput(repo);
			}
			selection = AdapterUtils.adapt(o, RevCommit.class);

