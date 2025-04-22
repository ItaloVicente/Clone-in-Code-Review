			PushBranchWizard wizard = null;
			Ref goodRef = null;
			for (int i = 0; i < commit.getRefCount(); i++) {
				Ref currentRef = commit.getRef(i);
				if (goodRef == null
						&& currentRef.getName().startsWith(Constants.R_HEADS)) {
					goodRef = currentRef;
				}
			}
			if (goodRef == null) {
				wizard = new PushBranchWizard(repo, commit.getId());
			} else {
				wizard = new PushBranchWizard(repo, goodRef);
			}
