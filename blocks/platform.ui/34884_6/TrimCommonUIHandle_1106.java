
package org.eclipse.ui.internal.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.WindowTrimProxy;

public class TrimArea {
	
	private class TrimLine {
		List controls = new ArrayList();

		int minorMax; // Default minimum for empty trim areas

		int resizableCount;

		int availableSpace;

		public TrimLine(int majorHint) {
			availableSpace = majorHint;
		}

		void addControl(Control ctrl, int tileLength, int minorSize, Control dragHandle) {
			if (dragHandle != null) {
				controls.add(dragHandle);
			}
			
			controls.add(ctrl);
			
			if (getData(ctrl).isResizeable()) {
				resizableCount++;
			}

			if (minorSize > minorMax) {
				minorMax = minorSize;
			}
			
			availableSpace -= tileLength;
		}
		
		int terminate() {
			for (Iterator ctrls = controls.iterator(); ctrls.hasNext();) {
				Control ctrl = (Control) ctrls.next();
				if (ctrl instanceof CBanner) {
					CBanner banner = (CBanner) ctrl;
					Point bannerPrefSize = (Point) banner.getData(PREFSIZE_DATA_ID);
					int realWidth = bannerPrefSize.x + (availableSpace / resizableCount);
					Point cbSize = banner.computeSize(realWidth, SWT.DEFAULT);
					banner.setData(PREFSIZE_DATA_ID, new Point(bannerPrefSize.x, cbSize.y));
					
					if (cbSize.y > minorMax)
						minorMax = cbSize.y;
				}
			}
			
			return minorMax;
		}
	}

	private static final IWindowTrim defaultData = new WindowTrimProxy(null, null, null, 0, true);

	private static IWindowTrim getData(Control control) {
		IWindowTrim data = (IWindowTrim) control.getLayoutData();
		if (data == null) {
			data = defaultData;
		}

		return data;
	}

	private int fId;

	private String fDisplayName;

	private List lines = new ArrayList();
	
	private ArrayList fTrim;

	private static final String PREFSIZE_DATA_ID = "prefSize"; //$NON-NLS-1$
	private static final int MIN_BANNER_LEFT = 150;
	private static int TILE_SPACING = 2;
	private static int LINE_SPACING = 2;
	
	private Rectangle curRect = new Rectangle(0,0,0,0);

	public TrimArea(int id, String displayName) {
		fTrim = new ArrayList();
		fId = id;
		fDisplayName = displayName;
	}

	public boolean isEmpty() {
		return fTrim.isEmpty();
	}

	public Rectangle getCurRect() {
		return curRect;
	}
	
	public List getTrims() {
		List trim = new ArrayList(fTrim.size());
		Iterator d = fTrim.iterator();

		while (d.hasNext()) {
			TrimDescriptor desc = (TrimDescriptor) d.next();
			trim.add(desc.getTrim());
		}
		return trim;
	}

	public List getDescriptors() {
		return (List) fTrim.clone();
	}
	
	private Point getPrefSize(Control ctrl) {
		Point prefSize = ctrl.getSize();

		if ((prefSize.x == 0 || prefSize.y == 0) && !getData(ctrl).isResizeable()) {
			prefSize = ctrl.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			ctrl.setSize(prefSize);
		}
		
		if (getData(ctrl).isResizeable()) {
			if (ctrl instanceof CBanner) {
				CBanner banner = (CBanner) ctrl;
				prefSize.x = banner.getRightWidth() + banner.getBorderWidth() + MIN_BANNER_LEFT;
				prefSize.y = 0;  // No height for now, computed later
			}
			else if (getData(ctrl).getId().equals("org.eclipse.jface.action.StatusLineManager")) { //$NON-NLS-1$
				prefSize = new Point(250, 26);
			}
			else {
				prefSize = ctrl.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			
			ctrl.setData(PREFSIZE_DATA_ID, prefSize);
		}
		
		return prefSize;
	}
	
	public int computeWrappedTrim(int majorHint) {
		int totalMinor = 0;
		
		lines.clear();
		
		boolean isHorizontal = !isVertical();

		TrimLine curLine = new TrimLine(majorHint);
		lines.add(curLine);

		TrimCommonUIHandle dragHandle = null;
		
		List caches = getCaches();
		for (Iterator cacheIter = caches.iterator(); cacheIter.hasNext();) {
			SizeCache cache = (SizeCache) cacheIter.next();
			Control ctrl = cache.getControl();
			
			if (ctrl == null || !ctrl.getVisible())
				continue;
			
			if (ctrl instanceof TrimCommonUIHandle) {
				dragHandle = (TrimCommonUIHandle) ctrl;

				Point dhSize = dragHandle.getSize();
				if (dhSize.x == 0 || dhSize.y == 0)
					dragHandle.setSize(dragHandle.computeSize(SWT.DEFAULT, SWT.DEFAULT));

				continue;
			}
			
			Point prefSize = getPrefSize(ctrl);

			int tileLength = isHorizontal ? prefSize.x : prefSize.y;
			int minorSize = isHorizontal ? prefSize.y : prefSize.x;

			if (dragHandle != null) {
				Point dhSize = dragHandle.getSize();
				tileLength += isHorizontal ? dhSize.x : dhSize.y;
			}
			
			tileLength += TILE_SPACING;

			if (tileLength < curLine.availableSpace	|| curLine.controls.size() == 0) {
				curLine.addControl(ctrl, tileLength, minorSize, dragHandle);
			} else {
				totalMinor += curLine.terminate();
				
				curLine = new TrimLine(majorHint);
				lines.add(curLine);
				
				curLine.addControl(ctrl, tileLength, minorSize, dragHandle);
			}
			
			dragHandle = null;
		}

		totalMinor += curLine.terminate();

		totalMinor += (lines.size() + 1) * LINE_SPACING;
		
		return totalMinor;
	}

	static int tileCount = 0;
	public void tileTrim(int anchorX, int anchorY, int major) {
		curRect.x = anchorX;
		curRect.y = anchorY;
		
		boolean isHorizontal = !isVertical();

		int tileX = anchorX;
		int tileY = anchorY;
		
		if (isHorizontal) {
			tileX += TILE_SPACING;
			tileY += LINE_SPACING;
		}
		else {
			tileY += TILE_SPACING;
			tileX += LINE_SPACING;
		}
		
		for (Iterator lineIter = lines.iterator(); lineIter.hasNext();) {
			TrimLine line = (TrimLine) lineIter.next();
			
			int curExtraSpace = line.availableSpace;
			int curResizeCount = line.resizableCount;
			for (Iterator ctrlIter = line.controls.iterator(); ctrlIter.hasNext();) {
				Control ctrl = (Control) ctrlIter.next();
				
				Point prefSize = ctrl.getSize();
				if (getData(ctrl).isResizeable() && curResizeCount > 0) {
					Point cachedPrefSize = (Point) ctrl.getData(PREFSIZE_DATA_ID);
					prefSize.x = cachedPrefSize.x;
					prefSize.y = cachedPrefSize.y;
					
					int resizeAmount = curExtraSpace/curResizeCount;
					if (isHorizontal)
						prefSize.x += resizeAmount;
					else
						prefSize.y += resizeAmount;
					
					curExtraSpace -= resizeAmount;
					curResizeCount--;
					
					ctrl.setSize(prefSize);
				}
				
				ctrl.setLocation(tileX, tileY);

				if (isHorizontal)
					tileX += prefSize.x;
				else
					tileY += prefSize.y;
				
				if (!(ctrl instanceof TrimCommonUIHandle)) {
					if (isHorizontal)
						tileX += TILE_SPACING;
					else
						tileY += TILE_SPACING;
				}
			}
			
			if (isHorizontal) {
				tileY += (line.minorMax + LINE_SPACING);
				tileX = anchorX + TILE_SPACING;
			}
			else {
				tileX += (line.minorMax + LINE_SPACING);
				tileY = anchorY + TILE_SPACING;
			}
		}
		
		if (isHorizontal) {
			curRect.width = major;
			curRect.height = tileY - anchorY;
		}
		else {
			curRect.width = tileX - anchorX;
			curRect.height = major;
		}
	}
	
	public boolean isVertical() {
		return fId == SWT.LEFT || fId == SWT.RIGHT;
	}

	public int getId() {
		return fId;
	}

	public String getDisplayName() {
		return fDisplayName;
	}

	public void addTrim(TrimDescriptor desc) {
		fTrim.add(desc);
	}

	public void addTrim(TrimDescriptor desc, TrimDescriptor beforeMe) {
		int idx = fTrim.indexOf(beforeMe);
		if (idx == -1) {
			fTrim.add(desc);
		} else {
			ListIterator i = fTrim.listIterator(idx);
			i.add(desc);
		}
	}

	public void removeTrim(TrimDescriptor desc) {
		fTrim.remove(desc);
	}

	public boolean contains(TrimDescriptor desc) {
		return fTrim.contains(desc);
	}

	public List getCaches() {
		ArrayList result = new ArrayList(fTrim.size());
		Iterator d = fTrim.iterator();
		while (d.hasNext()) {
			TrimDescriptor desc = (TrimDescriptor) d.next();
			if (desc.getDockingCache() != null) {
				result.add(desc.getDockingCache());
			}
			result.add(desc.getCache());
		}
		return result;
	}
}
