package org.eclipse.jgit.junit;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.ContentMerger;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;
import org.eclipse.jgit.merge.MergeResult;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public final class MockContentMerger extends ContentMerger {

	public MockContentMerger(Repository db) {
		super(db);
	}

	@Override
	public MergeResult<RawText> merge(RawTextComparator cmp
			CanonicalTreeParser base
			CanonicalTreeParser theirs) throws IOException {
		String mergedContent = "custom merge - ";
		mergedContent += getRawText(base);
		mergedContent += ":";
		mergedContent += getRawText(ours);
		mergedContent += ":";
		mergedContent += getRawText(theirs);

		RawText rawMerge = new RawText(mergedContent.getBytes());
		MergeResult<RawText> mergeResult = new MergeResult<RawText>(
				Collections.singletonList(rawMerge));
		mergeResult.add(0
		return mergeResult;
	}

	private String getRawText(CanonicalTreeParser tree) throws IOException {
		return new RawText(db.open(tree.getEntryObjectId()
				.getCachedBytes()).getString(0);
	}
}
