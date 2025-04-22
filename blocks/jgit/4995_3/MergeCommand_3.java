					String msg = null;
					RevCommit newHead = null;
					EnumSet<MergeStatus> status = EnumSet
							.of(MergeStatus.MERGED);
					if (!squash) {
						newHead = new Git(getRepository()).commit()
