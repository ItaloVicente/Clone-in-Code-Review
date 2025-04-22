				}
				if (showUnbornHead) {
					Ref head = repo.exactRef(Constants.HEAD);
					if (head != null && head.isSymbolic()
							&& head.getObjectId() == null) {
						refs.add(new AdditionalRefNode(node, repo, head));
					}
				}
