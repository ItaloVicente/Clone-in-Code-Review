package org.eclipse.egit.ui.internal.commit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.commit.DiffStyleRangeFormatter.DiffStyleRange.Type;
import org.eclipse.egit.ui.internal.history.FileDiff;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

public class DiffStyleRangeFormatter extends DiffFormatter {

	public static class DiffStyleRange extends StyleRange {

		public enum Type {

			ADD,

			REMOVE,

			HUNK,

			OTHER,

		}

		public Type diffType = Type.OTHER;

		public Color lineBackground = null;

		public boolean similarTo(StyleRange style) {
			return super.similarTo(style) && style instanceof DiffStyleRange
					&& diffType == ((DiffStyleRange) style).diffType;
		}
	}

	private static class DocumentOutputStream extends OutputStream {

		private String charset;

		private IDocument document;

		private int offset;

		public DocumentOutputStream(IDocument document, int offset) {
			this.document = document;
			this.offset = offset;
		}

		private void write(String content) throws IOException {
			try {
				this.document.replace(this.offset, 0, content);
				this.offset += content.length();
			} catch (BadLocationException e) {
				throw new IOException(e);
			}
		}

		public void write(byte[] b, int off, int len) throws IOException {
			if (charset == null)
				write(new String(b, off, len));
			else
				write(new String(b, off, len, charset));
		}

		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}

		public void write(int b) throws IOException {
			write(new byte[] { (byte) b });
		}
	}

	private DocumentOutputStream stream;

	private List<DiffStyleRange> ranges = new ArrayList<DiffStyleRange>();

	public DiffStyleRangeFormatter(IDocument document, int offset) {
		super(new DocumentOutputStream(document, offset));
		this.stream = (DocumentOutputStream) getOutputStream();
	}

	public DiffStyleRangeFormatter(IDocument document) {
		this(document, document.getLength());
	}

	public DiffStyleRangeFormatter write(Repository repository, FileDiff diff)
			throws IOException {
		this.stream.charset = CompareUtils.getResourceEncoding(repository,
				diff.getPath());
		diff.outputDiff(null, repository, this, true);
		flush();
		return this;
	}

	public DiffStyleRange[] getRanges() {
		return this.ranges.toArray(new DiffStyleRange[this.ranges.size()]);
	}

	protected DiffStyleRange addRange(Type type) {
		DiffStyleRange range = new DiffStyleRange();
		range.start = stream.offset;
		range.diffType = type;
		ranges.add(range);
		return range;
	}

	protected void writeHunkHeader(int aStartLine, int aEndLine,
			int bStartLine, int bEndLine) throws IOException {
		DiffStyleRange range = addRange(Type.HUNK);
		super.writeHunkHeader(aStartLine, aEndLine, bStartLine, bEndLine);
		range.length = stream.offset - range.start;
	}

	protected void writeLine(char prefix, RawText text, int cur)
			throws IOException {
		if (prefix == ' ')
			super.writeLine(prefix, text, cur);
		else {
			DiffStyleRange range = addRange(prefix == '+' ? Type.ADD
					: Type.REMOVE);
			super.writeLine(prefix, text, cur);
			range.length = stream.offset - range.start;
		}
	}
}
