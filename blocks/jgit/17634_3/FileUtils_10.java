package org.eclipse.jgit.merge;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class TextMergeDriver implements IMergeDriver {
	private MergeResult<RawText> lowLevelResults;

	public int merge(Repository repository
			String[] commitNames)
			throws IOException {
		final SupportedAlgorithm diffAlg = repository.getConfig().getEnum(
				ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_ALGORITHM
				SupportedAlgorithm.HISTOGRAM);
		final MergeAlgorithm mergeAlgorithm = new MergeAlgorithm(
				DiffAlgorithm.getAlgorithm(diffAlg));

		lowLevelResults = contentMerge(mergeAlgorithm
		writeMergedContent(ours

		if (lowLevelResults.containsConflicts())
			return lowLevelResults.getConflictCount();
		return OK;
	}

	public String getName() {
		return JGitText.get().textMergeDriverName;
	}

	public MergeResult<RawText> getLowLevelResults() {
		return lowLevelResults;
	}

	private static void writeMergedContent(File file
			MergeResult<RawText> result
			throws IOException {
		MergeFormatter fmt = new MergeFormatter();
		OutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(file));
			fmt.formatMerge(stream
					Constants.CHARACTER_ENCODING);
		} finally {
			if (stream != null)
				stream.close();
		}
	}

	private static MergeResult<RawText> contentMerge(
			MergeAlgorithm mergeAlgorithm
			throws IOException {
		RawText baseText = getRawText(base);
		RawText ourText = getRawText(ours);
		RawText theirsText = getRawText(theirs);
		return mergeAlgorithm.merge(RawTextComparator.DEFAULT
				ourText
	}

	private static RawText getRawText(File file)
			throws IOException {
		if (file == null)
			return RawText.EMPTY_TEXT;
		return new RawText(file);
	}
}
