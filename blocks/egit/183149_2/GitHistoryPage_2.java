				GitInfo info = Adapters.adapt(o, GitInfo.class);
				if (info != null && info.getRepository() != null) {
					repo = info.getRepository();
					IPath gitPath = Path.fromPortableString(info.getGitPath());
					input = new HistoryPageInput(repo,
							new File[] { new File(gitPath.toOSString()) });
				} else {
					repo = Adapters.adapt(o, Repository.class);
					if (repo != null) {
						File file = Adapters.adapt(o, File.class);
						if (file == null) {
							input = new HistoryPageInput(repo);
						} else {
							input = new HistoryPageInput(repo,
									new File[] { file });
						}
