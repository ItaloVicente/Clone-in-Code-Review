			if (directories) {
				for (String dir : status.getUntrackedFolders()) {
					if (paths.isEmpty() || paths.contains(dir)) {
						if (!dryRun)
							FileUtils.delete(new File(repo.getWorkTree()
									FileUtils.RECURSIVE);
						files.add(dir + "/");
					}
				}
			}
