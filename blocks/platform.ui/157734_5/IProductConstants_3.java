package org.eclipse.e4.ui.internal.dialogs.about;

class HyperlinkRange {

	private int offset, length;

	public HyperlinkRange(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	public boolean isOffsetInRange(int offset) {
		return offset >= this.offset && offset < this.offset + this.length;
	}
}
