package org.eclipse.e4.ui.internal.css.swt.dom.scrollbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;


	private static final boolean DEBUG_KEEP_NATIVE = false;

	protected int fInitialDragPixel;

	protected Point fInitialDragPosition;

	protected final ScrollBar fScrollBar;

	private boolean fMouseNearScrollScrollBar;

	protected ScrollBarPositions fScrollBarPositions;

	protected Rectangle fHandleDrawnRect;

	protected IScrollBarSettings fScrollBarSettings;

	private boolean fVisible = true;

	private final boolean fInitialVisible;

	protected AbstractScrollHandler(ScrollBar scrollBar, IScrollBarSettings scrollBarSettings) {
		fScrollBar = scrollBar;
		boolean initialVisible = true;
		if (scrollBar != null) {
			initialVisible = scrollBar.getVisible();
		}
		this.fInitialVisible = initialVisible;
		this.fScrollBarSettings = scrollBarSettings;
	}

	public void install(AbstractThemedScrollBarAdapter abstractThemedScrollBarAdapter) {
		if (this.fScrollBar != null) {
			if (!DEBUG_KEEP_NATIVE) {
				fScrollBar.setVisible(false);
			}
			this.fScrollBar.addSelectionListener(abstractThemedScrollBarAdapter);
		}
	}

	public void uninstall(AbstractThemedScrollBarAdapter abstractThemedScrollBarAdapter, boolean disposing) {
		fInitialDragPosition = null;
		fScrollBarPositions = null;
		fHandleDrawnRect = null;
		if (this.fScrollBar != null && !this.fScrollBar.isDisposed() && !disposing) {
			this.fScrollBar.removeSelectionListener(abstractThemedScrollBarAdapter);
			if (!DEBUG_KEEP_NATIVE) {
				this.fScrollBar.setVisible(fInitialVisible);
			}
		}
	}

	public void setVisible(boolean visible) {
		this.fVisible = visible;
	}

	public boolean getVisible() {
		return this.fVisible;
	}


	public Rectangle getHandleRect() {
		return this.fHandleDrawnRect;
	}

	public abstract Rectangle computeProximityRect(Rectangle currClientArea);

	protected void checkScrollbarInvisible() {
		if (this.fScrollBar == null || !this.fScrollBarSettings.getScrollBarThemed()) {
			return;
		}
		if (!DEBUG_KEEP_NATIVE) {
			if (this.fScrollBar.isVisible()) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (fScrollBar != null && !fScrollBar.isDisposed()) {
							fScrollBar.setVisible(false);
						}
					}
				});
			}
		}
	}

	protected abstract Rectangle getFullBackgroundRect(Scrollable scrollable, Rectangle currClientArea,
			boolean considerMargins);

	public boolean startDragOnMouseDown(Scrollable scrollable, Point controlPos, Point currHorizontalAndTopPixel,
			Rectangle currClientArea) {
		if (this.fScrollBar == null || !this.fVisible || !this.fScrollBarSettings.getScrollBarThemed()) {
			return false;
		}
		Rectangle rect = this.getHandleRect();
		if (rect != null) {
			if (rect.contains(controlPos.x, controlPos.y)) {
				fInitialDragPosition = new Point(controlPos.x, controlPos.y);
				fInitialDragPixel = getRelevantPositionFromPos(currHorizontalAndTopPixel);
				return true;
			}
		}
		return false;
	}

	public boolean scrollOnMouseDown(Scrollable scrollable, Point controlPos,
			Rectangle currClientArea) {
		if (this.fScrollBar == null || !this.fVisible || fScrollBarPositions == null
				|| !this.fScrollBarSettings.getScrollBarThemed()) {
			return false;
		}
		Rectangle rect = this.getHandleRect();
		if (rect != null) {
			if (rect.contains(controlPos.x, controlPos.y)) {
				return false;
			}
		}
		Rectangle fullRect = this.getFullBackgroundRect(scrollable, currClientArea, true);
		if (fullRect != null) {
			if (fullRect.contains(controlPos.x, controlPos.y)) {
				int pos = this.getRelevantPositionFromPos(controlPos);
				pos = (int) fScrollBarPositions.convertFromScrollBarPosToControlPixel(pos);
				int selection = this.fScrollBar.getSelection();
				int pageIncrement = this.fScrollBar.getPageIncrement();
				if (pos > selection) {
					this.fScrollBar.setSelection(selection + pageIncrement);
				} else {
					this.fScrollBar.setSelection(selection - pageIncrement);
				}
				notifyScrollbarSelectionChanged(scrollable);
				return true;
			}
		}
		return false;
	}

	protected abstract int getRelevantPositionFromPos(Point styledTextPos);

	public boolean isDragging() {
		return fInitialDragPosition != null;
	}

	public void stopDragOnMouseUp(Scrollable scrollable) {
		if (fInitialDragPosition != null) {
			fInitialDragPosition = null;
		}
	}

	protected abstract void setPixel(Scrollable scrollable, int pixel);

	public boolean dragOnMouseMove(Scrollable scrollable, Point mousePos) {
		if (fInitialDragPosition != null && this.fScrollBarPositions != null && this.fScrollBar != null) {
			int currentMousePos = getRelevantPositionFromPos(mousePos);
			int initialMousePos = getRelevantPositionFromPos(fInitialDragPosition);

			int delta = (currentMousePos - initialMousePos);
			delta /= this.fScrollBarPositions.fPercentageOfClientAreaFromTotalArea;
			setPixel(scrollable, fInitialDragPixel + delta);
			notifyScrollbarSelectionChanged(scrollable);
			return true;
		}
		return false;
	}

	protected void notifyScrollbarSelectionChanged(Scrollable scrollable) {
		Event e = new Event();
		e.type = SWT.Selection;
		e.x = 0;
		e.y = 0;
		e.button = 0;
		e.stateMask = 0;
		e.count = 1;

		e.display = scrollable.getDisplay();
		e.widget = fScrollBar;
		e.time = 0;
		e.data = null;
		e.character = '\0';

		e.doit = true;

		fScrollBar.notifyListeners(SWT.Selection, e);
	}

	public void paintControl(GC gc, Rectangle currClientArea, Scrollable scrollable) {
		checkScrollbarInvisible();
		doPaintControl(gc, currClientArea, scrollable);
	}

	public abstract boolean computePositions(Rectangle currClientArea, Scrollable scrollable);

	protected abstract void doPaintControl(GC gc, Rectangle currClientArea, Scrollable scrollable);

	public int getMouseNearScrollScrollBarWidth() {
		return fScrollBarSettings.getMouseNearScrollScrollBarWidth();
	}

	public int getCurrentScrollBarWidth() {
		if (this.fMouseNearScrollScrollBar) {
			return getMouseNearScrollScrollBarWidth();
		}
		return this.fScrollBarSettings.getScrollBarWidth();
	}

	public void setCursorNearScroll(boolean cursorNearScroll) {
		this.fMouseNearScrollScrollBar = cursorNearScroll;
	}

	public boolean getMouseNearScrollScrollBar() {
		return this.fMouseNearScrollScrollBar;
	}


}
