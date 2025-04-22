					input = new HistoryPageInput(repo, new File[] { folder });
					showHead(repo);
					break;
				case REF:
					input = new HistoryPageInput(repo);
					showRef(((RefNode) repoNode).getObject(), repo);
					break;
				case ADDITIONALREF:
					input = new HistoryPageInput(repo);
					showRef(((AdditionalRefNode) repoNode).getObject(), repo);
					break;
				case TAG:
					input = new HistoryPageInput(repo);
					showTag(((TagNode) repoNode).getObject(), repo);
