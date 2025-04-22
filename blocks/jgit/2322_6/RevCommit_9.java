				if (replacements != null && replacements.size() > 0) {
					ObjectId newParent = replacements.get(idBuffer);
					if (newParent != null) {
						idBuffer.fromObjectId(newParent);
						RevCommit parent = walk.lookupCommit(newParent);
						parent.flags |= RevWalk.GRAFTED;
					}
				}

