			PushBranchWizard wizard = null;
			Ref localBranch = null;
			for (int i = 0; i < commit.getRefCount(); i++) {
				Ref currentRef = commit.getRef(i);
				if (localBranch == null
						&& currentRef.getName().startsWith(Constants.R_HEADS)) {
					localBranch = currentRef;
				}
			}
			if (localBranch == null) {
				wizard = new PushBranchWizard(repo, commit.getId());
			} else {
				wizard = new PushBranchWizard(repo, localBranch);
			}
