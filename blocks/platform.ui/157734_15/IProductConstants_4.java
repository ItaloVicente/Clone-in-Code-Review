package org.eclipse.e4.ui.internal.dialogs.about;

final class HyperlinkRange {

	private final int offset;
	private final int length;

	public HyperlinkRange(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}

	public int offset() {
		return offset;
	}

	public int length() {
		return length;
	}

	public boolean contains(int offset) {
		return offset >= this.offset && offset < this.offset + this.length;
	}
}
