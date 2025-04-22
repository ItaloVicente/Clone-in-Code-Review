				if (bare)
					builder.setGitDir(directory);
				else {
					builder.setWorkTree(directory);
					if (gitDir == null)
						builder.setGitDir(new File(directory
				}
