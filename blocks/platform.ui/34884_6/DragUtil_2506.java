package org.eclipse.ui.internal.dnd;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.themes.ColorUtil;

public class DragBorder {
	private Composite clientControl = null;
	private Control dragControl = null;
	private Canvas border = null;

	private Color baseColor;
	private Color hilightColor;
	private boolean isHighlight;

	public DragBorder(Composite client, Control toDrag, boolean provideFrame) {
		clientControl = client;
		dragControl = toDrag;
		Point dragSize = toDrag.getSize();
		
		border = new Canvas(dragControl.getParent(), SWT.NONE);
		border.setSize(dragSize.x+2, dragSize.y+2);
		
		baseColor = border.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);
		RGB background  = border.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		RGB blended = ColorUtil.blend(baseColor.getRGB(), background);
		hilightColor = new Color(border.getDisplay(), blended);
		
		border.moveAbove(null);
		dragControl.moveAbove(null);
		
		if (provideFrame) {
			border.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					if (isHighlight) {
						e.gc.setForeground(hilightColor);
					}
					else {
						e.gc.setForeground(baseColor);
					}
					
					Rectangle bb = border.getBounds();
					e.gc.drawRectangle(0,0,bb.width-1, bb.height-1);
				}
			});
		}
	}
	
    
    public void setLocation(Point newPos, int alignment) {
    	if (alignment == SWT.CENTER) {
    		Point size = border.getSize();
    		border.setLocation(newPos.x - (size.x/2), newPos.y - (size.y/2));
    	}
    	else if (alignment == SWT.TOP) {
    		border.setLocation(newPos.x, newPos.y);
    	} else {
			border.setLocation(newPos.x, newPos.y - border.getSize().y);
		}
    	
		Rectangle bb = border.getBounds();
		Rectangle cr = clientControl.getClientArea();
		Geometry.moveInside(bb,cr);
		
		border.moveAbove(null);
		dragControl.moveAbove(null);
		
		dragControl.setLocation(bb.x+1, bb.y+1);
		border.setBounds(bb);
    }

	public void setHighlight(boolean highlight) {
		isHighlight = highlight;
		border.redraw();
	}

	public void dispose() {
		hilightColor.dispose();
		border.dispose();
	}


	public Rectangle getBounds() {
		return border.getBounds();
	}
}
