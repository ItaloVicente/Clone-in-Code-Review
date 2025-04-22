
			if (directories)
				for (String dir : notIgnoredDirs)
					if (paths.isEmpty() || paths.contains(dir)) {
						if (!dryRun)
							FileUtils.delete(new File(repo.getWorkTree()
									FileUtils.RECURSIVE);
						files.add(dir + "/");
					}
