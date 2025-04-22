				RevCommit current = revWalk.parseCommit(commit);
				RevObject any = revWalk
						.peel(revWalk.parseAny(ref.getObjectId()));
				if (!(any instanceof RevCommit))
					continue;
				RevCommit newTag = (RevCommit) any;
				if (newTag.getId().equals(commit))
					continue;

				if (isMergedInto(revWalk, newTag, current, searchDescendant)) {
					if (monitor.isCanceled())
						throw new OperationCanceledException();
					if (tagRef != null) {
						RevCommit oldTag = revWalk
								.parseCommit(tagRef.getObjectId());

						if (isMergedInto(revWalk, oldTag, newTag,
								searchDescendant))
							tagRef = ref;
					} else
