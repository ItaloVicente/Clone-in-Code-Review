				if (((Boolean) e.data).booleanValue()
						&& commitCombo.getItemCount() == 0) {
					for (RevCommit revCommit : getRevCommits())
						commitCombo.add(revCommit);
				}
				composite.layout(true);
