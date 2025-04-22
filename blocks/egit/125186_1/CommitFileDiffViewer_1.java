			if (repo == null || workTreePath == null) {
				repo = fileDiff.getRepository();
				if (repo == null || repo.isBare()) {
					return null;
				}
				workTreePath = new Path(repo.getWorkTree().getAbsolutePath());
			}
