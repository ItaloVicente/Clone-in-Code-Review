package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;

public class HyperlinkTokenScanner implements ITokenScanner {

	protected static final IToken HYPERLINK = new Token(
			new HyperlinkDamagerRepairer.HyperlinkTextAttribute(null));

	protected static final IToken DEFAULT = new Token(null);

	private int endOfRange;

	private int currentOffset;

	private int tokenStart;

	private final ISourceViewer viewer;

	private final IHyperlinkDetector[] hyperlinkDetectors;

	public HyperlinkTokenScanner(IHyperlinkDetector[] hyperlinkDetectors,
			ISourceViewer viewer) {
		this.hyperlinkDetectors = hyperlinkDetectors;
		this.viewer = viewer;
	}

	@Override
	public void setRange(IDocument document, int offset, int length) {
		Assert.isTrue(document == viewer.getDocument());
		this.endOfRange = offset + length;
		this.currentOffset = offset;
		this.tokenStart = -1;
	}

	@Override
	public IToken nextToken() {
		this.tokenStart = currentOffset;
		if (currentOffset >= endOfRange) {
			return Token.EOF;
		}
		for (IHyperlinkDetector hyperlinkDetector : hyperlinkDetectors) {
			IHyperlink[] newLinks = hyperlinkDetector.detectHyperlinks(viewer,
					new Region(currentOffset, 0), false);
			if (newLinks != null && newLinks.length > 0) {
				IRegion region = newLinks[0].getHyperlinkRegion();
				int end = Math.min(endOfRange,
						region.getOffset() + region.getLength());
				if (end > tokenStart) {
					currentOffset = end;
					return HYPERLINK;
				}
			}
		}
		currentOffset++;
		return DEFAULT;
	}

	@Override
	public int getTokenOffset() {
		return tokenStart;
	}

	@Override
	public int getTokenLength() {
		return currentOffset - tokenStart;
	}

}
