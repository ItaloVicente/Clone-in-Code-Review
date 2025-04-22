					if (i.hasNext()) {
						if (count++ <= MAXBRANCHES) {
							d.append(", "); //$NON-NLS-1$
						} else {
							d.append(NLS.bind(UIText.CommitMessageViewer_MoreBranches, Integer.valueOf(branches.size() - MAXBRANCHES)));
							break;
						}
					}
