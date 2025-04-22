				MergeResult<RawText> result = contentMerge(base, ours, theirs,
						attributes);

				if (ignoreConflicts) {
					result.setContainsConflicts(false);
					updateIndex(base, ours, theirs, result, attributes);
				} else {
