package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.themes.ColorUtil;

public class InsertCaret {
	private static final int width = 6; // the handle's 'thickness'
	private static final int pctInset = 10; // The percentage of the area left at each 'end'

	private Canvas caretControl;
	private Canvas end1;
	private Canvas end2;
	
	private Color baseColor;
	private Color hilightColor;
	private boolean isHighlight;
	
	public InsertCaret(Composite parent, Rectangle trimRect, int swtSide, int threshold) {
		baseColor = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);
		RGB background  = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB blended = ColorUtil.blend(baseColor.getRGB(), background);
		hilightColor = new Color(parent.getDisplay(), blended);

		createControl(parent, trimRect, swtSide, threshold);
	}

	private void createControl(Composite parent, Rectangle trimRect, int swtSide, int threshold) {
		int hDelta = trimRect.width/pctInset;
		int vDelta = trimRect.height/pctInset;
		caretControl = new Canvas (parent.getShell(), SWT.BORDER);
		
		end1 = new Canvas (parent.getShell(), SWT.BORDER);
		end1.setSize(width, width);
		end2 = new Canvas (parent.getShell(), SWT.BORDER);
		end2.setSize(width, width);
		
		Rectangle bb;
		switch (swtSide) {
		case SWT.TOP:
			caretControl.setSize(trimRect.width-(2*hDelta), width);
			caretControl.setLocation(trimRect.x + hDelta, trimRect.y + trimRect.height + threshold);
			bb = caretControl.getBounds();
			end1.setLocation(bb.x, bb.y-width);
			end2.setLocation((bb.x+bb.width)-width, bb.y-width);
			break;
		case SWT.BOTTOM:
			caretControl.setSize(trimRect.width-(2*hDelta), width);
			caretControl.setLocation(trimRect.x + hDelta, trimRect.y - threshold); 
			bb = caretControl.getBounds();
			end1.setLocation(bb.x, bb.y+width);
			end2.setLocation((bb.x+bb.width)-width, bb.y+width);
			break;
		case SWT.LEFT:
			caretControl.setSize(width, trimRect.height -(2*vDelta));
			caretControl.setLocation(trimRect.x + trimRect.width + threshold,
									trimRect.y + vDelta); 
			bb = caretControl.getBounds();
			end1.setLocation(bb.x-bb.width, bb.y);
			end2.setLocation(bb.x-bb.width, (bb.y+bb.height)-width);
			break;
		case SWT.RIGHT:
			caretControl.setSize(width, trimRect.height -(2*vDelta));
			caretControl.setLocation(trimRect.x - threshold,
									trimRect.y + vDelta); 
			bb = caretControl.getBounds();
			end1.setLocation(bb.x+bb.width, bb.y);
			end2.setLocation(bb.x+bb.width, (bb.y+bb.height)-width);
			break;
		}
		
		setHighlight(false);
		caretControl.moveAbove(null);
		end1.moveAbove(null);
		end2.moveAbove(null);
	}

	public void setHighlight(boolean highlight) {
		isHighlight = highlight;

		if (isHighlight) {
			caretControl.setBackground(hilightColor);
			end1.setBackground(hilightColor);
			end2.setBackground(hilightColor);
		}
		else {
			caretControl.setBackground(baseColor);
			end1.setBackground(baseColor);
			end2.setBackground(baseColor);
		}
	}

	public void dispose() {
		hilightColor.dispose();
		
		caretControl.dispose();
		end1.dispose();
		end2.dispose();
	}
}
