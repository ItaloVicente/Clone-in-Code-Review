				gen.push(null, repo.resolve(Constants.HEAD));
				if (!repo.isBare()) {
					DirCache dc = repo.readDirCache();
					int entry = dc.findEntry(path);
					if (0 <= entry)
						gen.push(null, dc.getEntry(entry).getObjectId());

					File inTree = new File(repo.getWorkTree(), path);
					if (repo.getFS().isFile(inTree)) {
						RawText rawText = getRawText(inTree);
						gen.push(null, rawText);
					}
				}
