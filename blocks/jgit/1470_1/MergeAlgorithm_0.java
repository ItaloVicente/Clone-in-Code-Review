				int conflictLen = Math.min(oursEndB - oursBeginB
						- theirsBeginB);
				int commonPrefix = 0;
				while (commonPrefix < conflictLen
						&& ours.equals(oursBeginB + commonPrefix
								theirsBeginB + commonPrefix))
					commonPrefix++;
				conflictLen -= commonPrefix;
				int commonSuffix = 0;
				while (commonSuffix < conflictLen
						&& ours.equals(oursEndB - commonSuffix - 1
								theirsEndB - commonSuffix - 1))
					commonSuffix++;
				conflictLen -= commonSuffix;

				if (commonPrefix > 0)
					result.add(1
							ConflictState.NO_CONFLICT);

