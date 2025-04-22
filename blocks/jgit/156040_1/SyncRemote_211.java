			if (branchFullName.startsWith(remotePrefix)) {
				remoteBranches.add(branchFullName.replaceFirst(remotePrefix
			} else if (branchFullName.startsWith(localPrefix)) {
				localBranches.add(branchFullName.replaceFirst(localPrefix
			} else {
				localBranches.add(branchFullName.substring(branchFullName.lastIndexOf("/") + 1));
			}
		}
	}
