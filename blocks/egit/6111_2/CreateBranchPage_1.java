						if (firstRemote == null)
							firstRemote = entry.getValue().getName();
					}
				}
				if (firstRemote != null) {
					this.branchCombo.setText(firstRemote);
					suggestBranchName(firstRemote);
