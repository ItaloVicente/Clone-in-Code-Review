			try {
				Map<String, Ref> map = myRepository.getRefDatabase().getRefs(
						Constants.R_HEADS);
				for (Entry<String, Ref> entry : map.entrySet()) {
					if (entry.getValue().getLeaf().getObjectId()
							.equals(myBaseCommit))
						this.branchCombo.add(entry.getValue().getName());
				}
				map = myRepository.getRefDatabase()
						.getRefs(Constants.R_REMOTES);
				for (Entry<String, Ref> entry : map.entrySet()) {
					if (entry.getValue().getLeaf().getObjectId()
							.equals(myBaseCommit))
						this.branchCombo.add(entry.getValue().getName());
				}
			} catch (IOException e) {
				Activator.logError(
						"Exception while trying to find Refs for Commit", e); //$NON-NLS-1$
			}
			this.branchCombo.setEnabled(this.branchCombo.getItemCount() > 1);
