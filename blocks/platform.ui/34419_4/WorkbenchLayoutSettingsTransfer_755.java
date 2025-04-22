package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.internal.layout.TrimCommonUIHandle;

public class WorkbenchLayout extends Layout {
	private static int defaultMargin = 5;

	private class TrimLine {
		List controls = new ArrayList();

		int minorMax = 0;

		int resizableCount = 0;

		int extraSpace = 0;
	}

	private class LeftBannerLayout extends Layout {

		@Override
		protected Point computeSize(Composite composite, int wHint, int hHint,
				boolean flushCache) {
			return new Point(wHint, WorkbenchLayout.this.topMax);
		}

		@Override
		protected void layout(Composite composite, boolean flushCache) {
		}

	}

	public static final String TRIMID_CMD_PRIMARY = "Command Primary"; //$NON-NLS-1$

	public static final String TRIMID_CMD_SECONDARY = "Command Secondary"; //$NON-NLS-1$

	public static final String TRIMID_VERTICAL1 = "vertical1"; //$NON-NLS-1$

	public static final String TRIMID_VERTICAL2 = "vertical2"; //$NON-NLS-1$

	public static final String TRIMID_STATUS = "Status"; //$NON-NLS-1$

	public static final String TRIMID_CENTER = "Center"; //$NON-NLS-1$

	public CBanner banner;

	private int topMax;

	public Composite centerComposite;

	private int spacing = 0;

	private TrimArea cmdPrimaryTrimArea;

	private TrimArea cmdSecondaryTrimArea;

	private TrimArea leftTrimArea;

	private TrimArea rightTrimArea;

	private TrimArea bottomTrimArea;

	private int horizontalHandleSize = -1;

	private int verticalHandleSize = -1;

	private List dragHandles;

	private static Composite layoutComposite;

	private static Rectangle clientRect;

	public WorkbenchLayout() {
		super();

		cmdPrimaryTrimArea = new TrimArea(TRIMID_CMD_PRIMARY, SWT.TOP,
				defaultMargin);
		cmdSecondaryTrimArea = new TrimArea(TRIMID_CMD_SECONDARY, SWT.TOP,
				defaultMargin);
		leftTrimArea = new TrimArea(TRIMID_VERTICAL1, SWT.LEFT, defaultMargin);
		rightTrimArea = new TrimArea(TRIMID_VERTICAL2, SWT.RIGHT, defaultMargin);
		bottomTrimArea = new TrimArea(TRIMID_STATUS, SWT.BOTTOM, defaultMargin);

		dragHandles = new ArrayList();
	}

	public void createCBanner(Composite workbenchComposite) {
		banner = new CBanner(workbenchComposite, SWT.NONE);
		banner.setSimple(false);
		banner.setRightWidth(175);
		banner.setLocation(0, 0);

		Composite bannerLeft = new Composite(banner, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				if (WorkbenchLayout.layoutComposite != null) {
					return WorkbenchLayout.this.computeSize(TRIMID_CMD_PRIMARY,
							wHint);
				}

				return super.computeSize(wHint, hHint, changed);
			}
		};
		bannerLeft.setLayout(new LeftBannerLayout());
		bannerLeft.setBackground(workbenchComposite.getDisplay()
				.getSystemColor(SWT.COLOR_DARK_BLUE));
		banner.setLeft(bannerLeft);

		Composite bannerRight = new Composite(banner, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				if (WorkbenchLayout.layoutComposite != null) {
					return WorkbenchLayout.this.computeSize(
							TRIMID_CMD_SECONDARY, wHint);
				}

				return super.computeSize(wHint, hHint, changed);
			}
		};
		bannerRight.setBackground(workbenchComposite.getDisplay()
				.getSystemColor(SWT.COLOR_DARK_BLUE));
		banner.setRight(bannerRight);

		bannerRight.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
			}

			@Override
			public void controlResized(ControlEvent e) {
				Composite leftComp = (Composite) e.widget;
				leftComp.getShell().layout(true);
			}
		});

		banner.moveBelow(null);
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint,
			boolean flushCache) {
		Point size = new Point(wHint, hHint);
		if (size.x == SWT.DEFAULT) {
			size.x = 300;
		}
		if (size.y == SWT.DEFAULT) {
			size.y = 300;
		}
		return size;
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		layoutComposite = composite;
		clientRect = composite.getClientArea();

		resetDragHandles();

		if (useCBanner()) {
			banner.moveBelow(null);

			Point bannerSize = banner
					.computeSize(clientRect.width, SWT.DEFAULT);

			topMax = bannerSize.y;

			banner.setSize(bannerSize);
		} else {
			Point c1Size = computeSize(TRIMID_CMD_PRIMARY, clientRect.width);
			Point c2Size = computeSize(TRIMID_CMD_SECONDARY, clientRect.width);

			topMax = c1Size.y + c2Size.y;
		}

		Point v1Size = computeSize(TRIMID_VERTICAL1, clientRect.height - topMax);
		Point v2Size = computeSize(TRIMID_VERTICAL2, clientRect.height - topMax);

		computeSize(TRIMID_STATUS, clientRect.width - (v1Size.x + v2Size.x));

		if (useCBanner()) {
			Point leftLoc = banner.getLeft().getLocation();
			cmdPrimaryTrimArea.areaBounds.x = leftLoc.x;
			cmdPrimaryTrimArea.areaBounds.y = leftLoc.y;
		} else {
			cmdPrimaryTrimArea.areaBounds.x = 0;
			cmdPrimaryTrimArea.areaBounds.y = 0;
		}
		layoutTrim(cmdPrimaryTrimArea, cmdPrimaryTrimArea.areaBounds);

		if (useCBanner()) {
			Point rightLoc = banner.getRight().getLocation();
			cmdSecondaryTrimArea.areaBounds.x = rightLoc.x;
			cmdSecondaryTrimArea.areaBounds.y = rightLoc.y;
		} else {
			cmdSecondaryTrimArea.areaBounds.x = 0;
			cmdSecondaryTrimArea.areaBounds.y = cmdPrimaryTrimArea.areaBounds.height;
		}
		layoutTrim(cmdSecondaryTrimArea, cmdSecondaryTrimArea.areaBounds);

		leftTrimArea.areaBounds.x = 0;
		leftTrimArea.areaBounds.y = topMax;
		layoutTrim(leftTrimArea, leftTrimArea.areaBounds);

		rightTrimArea.areaBounds.x = clientRect.width
				- rightTrimArea.areaBounds.width;
		rightTrimArea.areaBounds.y = topMax;
		layoutTrim(rightTrimArea, rightTrimArea.areaBounds);

		bottomTrimArea.areaBounds.x = leftTrimArea.areaBounds.width;
		bottomTrimArea.areaBounds.y = clientRect.height
				- bottomTrimArea.areaBounds.height;
		layoutTrim(bottomTrimArea, bottomTrimArea.areaBounds);

		layoutCenter();
	}

	private boolean useCBanner() {
		return (banner != null && banner.getVisible());
	}

	private TrimArea getTrimArea(String areaId) {
		if (TRIMID_CMD_PRIMARY.equals(areaId)) {
			return cmdPrimaryTrimArea;
		}
		if (TRIMID_CMD_SECONDARY.equals(areaId)) {
			return cmdSecondaryTrimArea;
		}
		if (TRIMID_VERTICAL1.equals(areaId)) {
			return leftTrimArea;
		}
		if (TRIMID_VERTICAL2.equals(areaId)) {
			return rightTrimArea;
		}
		if (TRIMID_STATUS.equals(areaId)) {
			return bottomTrimArea;
		}

		return null;
	}

	private List getTrimContents(String areaId) {
		List trimContents = new ArrayList();
		Control[] children = layoutComposite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i].isDisposed() || !children[i].getVisible()) {
				continue;
			}

			if (children[i].getLayoutData() instanceof TrimLayoutData) {
				TrimLayoutData tlData = (TrimLayoutData) children[i]
						.getLayoutData();
				if (tlData.areaId.equals(areaId)) {
					trimContents.add(children[i]);
				}
			}
		}

		return trimContents;
	}

	private void layoutCenter() {
		if (centerComposite != null) {
			Rectangle areaBounds = new Rectangle(
					leftTrimArea.areaBounds.x + leftTrimArea.areaBounds.width,
					topMax,
					clientRect.width
							- (leftTrimArea.areaBounds.width + rightTrimArea.areaBounds.width),
					clientRect.height
							- (topMax + bottomTrimArea.areaBounds.height));

			centerComposite.setBounds(areaBounds);
		}
	}

	private Point computeSize(String areaId, int majorHint) {
		TrimArea trimArea = getTrimArea(areaId);
		boolean horizontal = trimArea.orientation == SWT.TOP
				|| trimArea.orientation == SWT.BOTTOM;

		trimArea.trimContents = getTrimContents(trimArea.areaId);

		trimArea.trimLines = computeWrappedTrim(trimArea, majorHint);

		int minorMax = 0;
		for (Iterator iter = trimArea.trimLines.iterator(); iter.hasNext();) {
			TrimLine curLine = (TrimLine) iter.next();
			minorMax += curLine.minorMax;
		}
		minorMax = Math.max(minorMax, trimArea.defaultMinor);

		Point computedSize = new Point(0, 0);
		if (horizontal) {
			computedSize.x = trimArea.areaBounds.width = majorHint;
			computedSize.y = trimArea.areaBounds.height = minorMax;
		} else {
			computedSize.x = trimArea.areaBounds.width = minorMax;
			computedSize.y = trimArea.areaBounds.height = majorHint;
		}

		return computedSize;
	}

	private List computeWrappedTrim(TrimArea trimArea, int majorHint) {
		boolean horizontal = trimArea.orientation == SWT.TOP
				|| trimArea.orientation == SWT.BOTTOM;
		List lines = new ArrayList();
		TrimLine curLine = new TrimLine();
		lines.add(curLine);
		curLine.minorMax = trimArea.defaultMinor;

		int tilePos = 0;
		for (Iterator iter = trimArea.trimContents.iterator(); iter.hasNext();) {
			Control control = (Control) iter.next();
			TrimLayoutData td = (TrimLayoutData) control.getLayoutData();

			Point prefSize;
			if (horizontal) {
				prefSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			} else {
				prefSize = control.computeSize(SWT.DEFAULT, majorHint);
			}

			int curTileSize = horizontal ? prefSize.x : prefSize.y;

			if (curLine.controls.size() > 0) {
				curTileSize += spacing;
			}

			if (td.listener != null) {
				curTileSize += horizontal ? horizontalHandleSize
						: verticalHandleSize;
			}

			if ((tilePos + curTileSize) <= majorHint
					|| curLine.controls.size() == 0) {
				curLine.controls.add(control);

				int minorSize = horizontal ? prefSize.y : prefSize.x;
				if (minorSize > curLine.minorMax) {
					curLine.minorMax = minorSize;
				}

				tilePos += curTileSize;
			} else {
				curLine.extraSpace = majorHint - tilePos;

				curLine = new TrimLine();
				lines.add(curLine);

				curLine.controls.add(control);

				int minorSize = horizontal ? prefSize.y : prefSize.x;
				if (minorSize > curLine.minorMax) {
					curLine.minorMax = minorSize;
				}

				tilePos = curTileSize;
			}

			if ((td.flags & TrimLayoutData.GROWABLE) != 0) {
				curLine.resizableCount++;
			}
		}

		curLine.extraSpace = majorHint - tilePos;

		return lines;
	}

	private void layoutTrim(TrimArea trimArea, Rectangle areaBounds) {
		boolean horizontal = trimArea.orientation == SWT.TOP
				|| trimArea.orientation == SWT.BOTTOM;

		int areaMajor = horizontal ? areaBounds.width : areaBounds.height;

		List tileAreas = trimArea.trimLines;

		int tilePosMinor = horizontal ? areaBounds.y : areaBounds.x;
		for (Iterator areaIter = tileAreas.iterator(); areaIter.hasNext();) {
			TrimLine curLine = (TrimLine) areaIter.next();

			int resizePadding = 0;
			if (curLine.resizableCount > 0 && curLine.extraSpace > 0) {
				resizePadding = curLine.extraSpace / curLine.resizableCount;
			}

			int tilePosMajor = horizontal ? areaBounds.x : areaBounds.y;
			for (Iterator iter = curLine.controls.iterator(); iter.hasNext();) {
				Control control = (Control) iter.next();
				TrimLayoutData td = (TrimLayoutData) control.getLayoutData();
				Point prefSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);

				int major = horizontal ? prefSize.x : prefSize.y;
				int minor = horizontal ? prefSize.y : prefSize.x;

				if (major > areaMajor) {
					major = areaMajor;
				}

				if ((td.flags & TrimLayoutData.GRAB_EXCESS_MINOR) != 0) {
					minor = curLine.minorMax;
				}

				if ((td.flags & TrimLayoutData.GROWABLE) != 0) {
					major += resizePadding;
				}

				if (td.listener != null && createHandles()) {
					TrimCommonUIHandle handle = getDragHandle(trimArea.orientation);

					if (horizontal) {
						handle.setBounds(tilePosMajor, tilePosMinor,
								getHandleSize(true), curLine.minorMax);
					} else {
						handle.setBounds(tilePosMinor, tilePosMajor,
								curLine.minorMax, getHandleSize(false));
					}

					tilePosMajor += horizontal ? getHandleSize(true)
							: getHandleSize(false);
				}

				switch (trimArea.orientation) {
				case SWT.TOP:
					control.setBounds(tilePosMajor, tilePosMinor, major, minor);
					break;
				case SWT.LEFT:
					control.setBounds(tilePosMinor, tilePosMajor, minor, major);
					break;
				case SWT.RIGHT:
					int rightEdge = tilePosMinor + curLine.minorMax;
					control.setBounds(rightEdge - minor, tilePosMajor, minor,
							major);
					break;
				case SWT.BOTTOM:
					int bottomEdge = tilePosMinor + curLine.minorMax;
					control.setBounds(tilePosMajor, bottomEdge - minor, major,
							minor);
					break;
				}

				tilePosMajor += major + spacing;
			}
			tilePosMinor += curLine.minorMax;
			tilePosMajor = horizontal ? areaBounds.x : areaBounds.y;
		}
	}

	private boolean createHandles() {
		return false;
	}

	private void resetDragHandles() {
		for (Iterator iter = dragHandles.iterator(); iter.hasNext();) {
		}
	}

	private TrimCommonUIHandle getDragHandle(int orientation) {

		for (Iterator iter = dragHandles.iterator(); iter.hasNext();) {
			TrimCommonUIHandle handle = (TrimCommonUIHandle) iter.next();
			return handle;
		}

		System.out.println("new Handle"); //$NON-NLS-1$
		return null;
	}

	public static int getOrientation(String areaId) {
		if (TRIMID_CMD_PRIMARY.equals(areaId)) {
			return SWT.TOP;
		}
		if (TRIMID_CMD_SECONDARY.equals(areaId)) {
			return SWT.TOP;
		}
		if (TRIMID_VERTICAL1.equals(areaId)) {
			return SWT.LEFT;
		}
		if (TRIMID_VERTICAL2.equals(areaId)) {
			return SWT.RIGHT;
		}
		if (TRIMID_STATUS.equals(areaId)) {
			return SWT.BOTTOM;
		}

		return SWT.NONE;
	}

	private int getHandleSize(boolean horizontal) {
		if (horizontal && horizontalHandleSize != -1) {
			return horizontalHandleSize;
		}

		if (!horizontal && verticalHandleSize != -1) {
			return verticalHandleSize;
		}

		CoolBar bar = new CoolBar(layoutComposite, horizontal ? SWT.HORIZONTAL
				: SWT.VERTICAL);

		CoolItem item = new CoolItem(bar, SWT.NONE);

		Label ctrl = new Label(bar, SWT.PUSH);
		ctrl.setText("Button 1"); //$NON-NLS-1$
		Point size = ctrl.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		Point ps = item.computeSize(size.x, size.y);
		item.setPreferredSize(ps);
		item.setControl(ctrl);

		bar.pack();

		Point bl = ctrl.getLocation();
		Point cl = bar.getLocation();

		ctrl.dispose();
		item.dispose();
		bar.dispose();

		int length;
		if (horizontal) {
			length = bl.x - cl.x;
			horizontalHandleSize = length;
		} else {
			length = bl.y - cl.y;
			verticalHandleSize = length;
		}

		return length;
	}

}
