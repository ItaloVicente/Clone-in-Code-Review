				Type type = null;
				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					boolean equalContent = compareVersionIterator
							.getEntryObjectId().equals(
									baseVersionIterator.getEntryObjectId());
					type = equalContent ? Type.FILE_BOTH_SIDES_SAME
							: Type.FILE_BOTH_SIDES_DIFFER;
				} else if (compareVersionIterator != null
						&& baseVersionIterator == null) {
					type = Type.FILE_DELETED;
				} else if (compareVersionIterator == null
						&& baseVersionIterator != null) {
					type = Type.FILE_ADDED;
				}
