package org.eclipse.jgit.merge;

import java.io.IOException;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

abstract public class ContentMerger {

	protected final Repository db;

	public static ContentMerger getDefaultContentMerger(
			final Repository repository) {
		return new ContentMerger(repository) {

			@Override
			public MergeResult<RawText> merge(RawTextComparator cmp
					CanonicalTreeParser base
					CanonicalTreeParser theirs) throws IOException {
				RawText baseText = base == null ? RawText.EMPTY_TEXT
						: ResolveMerger.getRawText(base.getEntryObjectId()
								repository);
				RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
						: ResolveMerger.getRawText(theirs.getEntryObjectId()
								repository);
				RawText oursText = ours == null ? RawText.EMPTY_TEXT
						: ResolveMerger.getRawText(ours.getEntryObjectId()
								repository);
				MergeAlgorithm mergeAlgorithm = getMergeAlgorithm(repository);
				MergeResult<RawText> result = mergeAlgorithm.merge(
						RawTextComparator.DEFAULT
						theirsText);
				return result;
			}

			private MergeAlgorithm getMergeAlgorithm(final Repository repo) {
				SupportedAlgorithm diffAlg = repo.getConfig().getEnum(
						ConfigConstants.CONFIG_DIFF_SECTION
						ConfigConstants.CONFIG_KEY_ALGORITHM
						SupportedAlgorithm.HISTOGRAM);
				MergeAlgorithm mergeAlgorithm = new MergeAlgorithm(
						DiffAlgorithm.getAlgorithm(diffAlg));
				return mergeAlgorithm;
			}

		};
	}

	public ContentMerger(Repository db) {
		this.db = db;
	}

	public abstract MergeResult<RawText> merge(RawTextComparator cmp
			CanonicalTreeParser base
			CanonicalTreeParser theirs) throws IOException;

}
