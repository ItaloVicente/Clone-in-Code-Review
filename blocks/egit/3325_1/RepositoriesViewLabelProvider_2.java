
			case REF:
			case TAG:
				StyledString styled = null;
				String nodeText = getSimpleText(node);
				if (nodeText != null) {
					styled = new StyledString(nodeText);
					if (verboseBranchMode) {
						RevCommit latest = getLatestCommit(node);
						if (latest != null)
							styled.append(' ' + latest.abbreviate(7).name()
									+ ' ' + latest.getShortMessage(),
									StyledString.QUALIFIER_STYLER);
					}
				}
				return styled;
