package org.eclipse.e4.ui.internal.css.swt.dom.scrollbar;

import org.eclipse.swt.graphics.Rectangle;

public abstract class ScrollBarPositions {

	protected int fMinimum;

	protected int fMaximum;

	protected int fPixel;

	protected int fSize;

	protected int fLargeness;

	protected int fScrollBarPos;

	protected int fScrollBarSize;

	protected double fPercentageOfClientAreaFromTotalArea;

	private int fMinimumScrollBarSize = 30;

	private int fScrollBarDiff;

	public ScrollBarPositions(int minimum, int maximum, int pixel, int size, int largeness) {
		this.fMinimum = minimum;
		this.fMaximum = maximum;
		this.fPixel = pixel;
		this.fSize = size;
		this.fLargeness = largeness;

		double total = maximum - minimum;
		double percentageOfClientAreaFromTotalArea = size / total;

		int scrollBarPos;
		int scrollBarSize = (int) Math.round((size * percentageOfClientAreaFromTotalArea));
		if (scrollBarSize < fMinimumScrollBarSize) {
			int diff = fMinimumScrollBarSize - scrollBarSize;
			percentageOfClientAreaFromTotalArea = (fSize - diff) / total;
			scrollBarPos = (int) (fPixel * percentageOfClientAreaFromTotalArea);
			scrollBarSize = fMinimumScrollBarSize;
			this.fScrollBarDiff = diff;
		} else {
			percentageOfClientAreaFromTotalArea = fSize / total;
			this.fScrollBarDiff = 0;
			scrollBarPos = (int) Math.round((fPixel * percentageOfClientAreaFromTotalArea));
		}

		this.fPercentageOfClientAreaFromTotalArea = percentageOfClientAreaFromTotalArea;
		this.fScrollBarPos = scrollBarPos;
		this.fScrollBarSize = scrollBarSize;
	}

	public double convertFromScrollBarPosToControlPixel(int pos) {
		return pos * (double) (fMaximum) / (fSize - fScrollBarDiff);
	}

	public abstract Rectangle getHandleDrawRect(int lineWidth);

	public static class ScrollBarPositionsVertical extends ScrollBarPositions {

		public ScrollBarPositionsVertical(int minimum, int maximum, int topPixel, int clientAreaHeight,
				int clientAreaWidth) {
			super(minimum, maximum, topPixel, clientAreaHeight, clientAreaWidth);
		}

		@Override
		public Rectangle getHandleDrawRect(int lineWidth) {
			return new Rectangle(fLargeness - lineWidth, fScrollBarPos, lineWidth, fScrollBarSize);
		}

	}

	public static class ScrollBarPositionsHorizontal extends ScrollBarPositions {

		public ScrollBarPositionsHorizontal(int minimum, int maximum, int horizontalPixel, int clientAreaHeight,
				int clientAreaWidth) {
			super(minimum, maximum, horizontalPixel, clientAreaWidth, clientAreaHeight);
		}

		@Override
		public Rectangle getHandleDrawRect(int lineWidth) {
			return new Rectangle(fScrollBarPos, fLargeness - lineWidth, fScrollBarSize, lineWidth);
		}
	}

}
