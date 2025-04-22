				branchName = node.getRepository().getFullBranch();
				if (branchName == null)
					return image;
				if (refName.startsWith(Constants.R_HEADS)) {
					compareString = refName;
				} else if (refName.startsWith(Constants.R_TAGS)) {
					compareString = node.getRepository().mapTag(refName)
							.getObjId().getName();
				} else if (refName.startsWith(Constants.R_REMOTES)) {
					compareString = node.getRepository().mapCommit(refName)
							.getCommitId().getName();
				} else {
					return image;
