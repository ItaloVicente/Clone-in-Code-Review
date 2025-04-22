package org.eclipse.egit.ui.internal.commit;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.diff.DiffEntry;

public class LogicalLineNumberProvider implements ILogicalLineNumberProvider {

	private final @NonNull DiffEntry.Side side;

	private final @NonNull ITextViewer viewer;

	public LogicalLineNumberProvider(@NonNull DiffEntry.Side side,
			@NonNull ITextViewer viewer) {
		this.side = side;
		this.viewer = viewer;
	}

	@Override
	public int getLogicalLine(int lineNumber) {
		IDocument document = viewer.getDocument();
		if (document instanceof DiffDocument) {
			return ((DiffDocument) document).getLogicalLine(lineNumber, side);
		}
		return lineNumber;
	}

	@Override
	public int getMaximum() {
		IDocument document = viewer.getDocument();
		if (document instanceof DiffDocument) {
			return ((DiffDocument) document).getMaximumLineNumber(side);
		}
		return -1;
	}
}
