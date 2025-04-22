							StyledString.DECORATIONS_STYLER);
					refName.append(']', StyledString.DECORATIONS_STYLER);
					refId = ref.getLeaf().getObjectId();
				} else
					refId = ref.getObjectId();

				refName.append(' ');
				RevCommit commit = getLatestCommit(node);
				if (commit != null)
					refName.append(abbreviate(commit),
							StyledString.QUALIFIER_STYLER)
							.append(' ')
							.append(commit.getShortMessage(),
									StyledString.QUALIFIER_STYLER);
				else
					refName.append(abbreviate(refId),
