				Repository repo = diff.getRepository();
				boolean workTreeFileExists = false;
				if (!repo.isBare()) {
					String path = new Path(repo.getWorkTree().getAbsolutePath())
							.append(diff.getPath()).toOSString();
					workTreeFileExists = new File(path).exists();
				}
