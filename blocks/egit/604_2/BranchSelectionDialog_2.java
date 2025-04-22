				boolean oneSelected = branchTree.getSelection().length == 1;

				String refName = refNameFromDialog();

				boolean headSelected = Constants.HEAD.equals(refName);

				boolean tagSelected = refName!=null && refName.startsWith(
						Constants.R_TAGS);

				boolean branchSelected = refName!=null && (refName.startsWith(Constants.R_HEADS) || refName.startsWith(Constants.R_REMOTES));

				confirmationBtn.setEnabled(oneSelected && branchSelected && !headSelected
						&& !tagSelected);
				renameButton.setEnabled(oneSelected && branchSelected && !headSelected && !tagSelected);

				newButton.setEnabled(oneSelected && branchSelected && !tagSelected);
