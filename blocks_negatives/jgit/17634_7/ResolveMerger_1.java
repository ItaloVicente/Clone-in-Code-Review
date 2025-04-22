			MergeResult<RawText> result = contentMerge(base, ours, theirs);
			File of = writeMergedFile(result);
			updateIndex(base, ours, theirs, result, of);
			if (result.containsConflicts())
				unmergedPaths.add(tw.getPathString());
			modifiedFiles.add(tw.getPathString());
