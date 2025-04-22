		case STASH:
			List<StashedCommitNode> stashNodes = new ArrayList<StashedCommitNode>();
			int index = 0;
			try {
				for (RevCommit commit : Git.wrap(repo).stashList().call())
					stashNodes.add(new StashedCommitNode(node, repo, index++,
							commit));
			} catch (Exception e) {
				handleException(e, node);
			}
			return stashNodes.toArray();
