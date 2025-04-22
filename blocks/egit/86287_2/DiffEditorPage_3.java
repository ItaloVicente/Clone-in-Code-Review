package org.eclipse.egit.ui.internal.commit;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.internal.commit.DiffStyleRangeFormatter.DiffStyleRange;
import org.eclipse.egit.ui.internal.commit.DiffStyleRangeFormatter.FileDiffRange;
import org.eclipse.egit.ui.internal.history.FileDiff;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

public class DiffDocument extends Document {

	static final String HEADLINE_CONTENT_TYPE = "_egit_diff_headline"; //$NON-NLS-1$

	static final String HUNK_CONTENT_TYPE = "_egit_diff_hunk"; //$NON-NLS-1$

	static final String ADDED_CONTENT_TYPE = "_egit_diff_added"; //$NON-NLS-1$

	static final String REMOVED_CONTENT_TYPE = "_egit_diff_removed"; //$NON-NLS-1$

	private DiffStyleRange[] ranges;

	private FileDiffRange[] fileRanges;

	private Pattern newPathPattern;

	private Pattern oldPathPattern;

	private Repository defaultRepository;

	private FileDiff defaultFileDiff;

	public DiffDocument() {
		super();
	}

	public DiffDocument(String text) {
		super(text);
	}

	public void connect(DiffStyleRangeFormatter formatter) {
		ranges = formatter.getRanges();
		fileRanges = formatter.getFileRanges();
		if ((fileRanges == null || fileRanges.length == 0)
				&& defaultRepository != null && defaultFileDiff != null) {
			fileRanges = new FileDiffRange[] { new FileDiffRange(
					defaultRepository, defaultFileDiff, 0, getLength()) };
		}
		newPathPattern = Pattern.compile(
				Pattern.quote(formatter.getNewPrefix()) + "\\S+"); //$NON-NLS-1$
		oldPathPattern = Pattern.compile(
				Pattern.quote(formatter.getOldPrefix()) + "\\S+"); //$NON-NLS-1$
		IDocumentPartitioner partitioner = new FastPartitioner(
				new DiffPartitionTokenScanner(),
				new String[] { IDocument.DEFAULT_CONTENT_TYPE,
						HEADLINE_CONTENT_TYPE, HUNK_CONTENT_TYPE,
						ADDED_CONTENT_TYPE, REMOVED_CONTENT_TYPE });
		IDocumentPartitioner oldPartitioner = getDocumentPartitioner();
		if (oldPartitioner != null) {
			oldPartitioner.disconnect();
		}
		partitioner.connect(this);
		setDocumentPartitioner(partitioner);
	}

	public void setDefault(Repository repository, FileDiff fileDiff) {
		defaultRepository = repository;
		defaultFileDiff = fileDiff;
	}

	DiffStyleRange[] getRanges() {
		return ranges;
	}

	FileDiffRange[] getFileRanges() {
		return fileRanges;
	}

	Pattern getPathPattern(DiffEntry.Side side) {
		switch (side) {
		case OLD:
			return oldPathPattern;
		default:
			return newPathPattern;
		}
	}

	private class DiffPartitionTokenScanner implements IPartitionTokenScanner {

		private final Token HEADLINE_TOKEN = new Token(HEADLINE_CONTENT_TYPE);

		private final Token HUNK_TOKEN = new Token(HUNK_CONTENT_TYPE);

		private final Token ADDED_TOKEN = new Token(ADDED_CONTENT_TYPE);

		private final Token DELETED_TOKEN = new Token(REMOVED_CONTENT_TYPE);

		private final Token OTHER_TOKEN = new Token(
				IDocument.DEFAULT_CONTENT_TYPE);

		private int currentOffset;

		private int end;

		private int tokenStart;

		private int currIdx;

		@Override
		public void setRange(IDocument document, int offset, int length) {
			Assert.isLegal(document == DiffDocument.this);
			currentOffset = offset;
			end = offset + length;
			tokenStart = -1;
		}

		@Override
		public IToken nextToken() {
			if (tokenStart < 0) {
				DiffStyleRange key = new DiffStyleRange();
				key.start = currentOffset;
				key.length = 0;
				currIdx = Arrays.binarySearch(DiffDocument.this.ranges, key,
						(a, b) -> {
							if (a.start > b.start + b.length) {
								return 1;
							}
							if (b.start > a.start + a.length) {
								return -1;
							}
							return 0;
						});
				if (currIdx < 0) {
					currIdx = -(currIdx + 1);
				}
			}
			tokenStart = currentOffset;
			if (currentOffset < end) {
				if (currIdx >= DiffDocument.this.ranges.length) {
					currentOffset = end;
					return OTHER_TOKEN;
				}
				if (currentOffset < DiffDocument.this.ranges[currIdx].start) {
					currentOffset = DiffDocument.this.ranges[currIdx].start;
					return OTHER_TOKEN;
				}
				currentOffset += DiffDocument.this.ranges[currIdx].length
						- (currentOffset
								- DiffDocument.this.ranges[currIdx].start);
				switch (DiffDocument.this.ranges[currIdx++].diffType) {
				case HEADLINE:
					return HEADLINE_TOKEN;
				case HUNK:
					return HUNK_TOKEN;
				case ADD:
					return ADDED_TOKEN;
				case REMOVE:
					return DELETED_TOKEN;
				default:
					return OTHER_TOKEN;
				}
			}
			return Token.EOF;
		}

		@Override
		public int getTokenOffset() {
			return tokenStart;
		}

		@Override
		public int getTokenLength() {
			return currentOffset - tokenStart;
		}

		@Override
		public void setPartialRange(IDocument document, int offset, int length,
				String contentType, int partitionOffset) {
			setRange(document, offset, length);
		}

	}
}
