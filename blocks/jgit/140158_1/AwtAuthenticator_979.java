
package org.eclipse.jgit.awtui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;

import org.eclipse.jgit.awtui.CommitGraphPane.GraphCellRender;
import org.eclipse.jgit.awtui.SwingCommitList.SwingLane;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revplot.AbstractPlotRenderer;
import org.eclipse.jgit.revplot.PlotCommit;

final class AWTPlotRenderer extends AbstractPlotRenderer<SwingLane
		implements Serializable {
	private static final long serialVersionUID = 1L;

	final GraphCellRender cell;

	transient Graphics2D g;

	AWTPlotRenderer(GraphCellRender c) {
		cell = c;
	}

	void paint(Graphics in
		g = (Graphics2D) in.create();
		try {
			final int h = cell.getHeight();
			g.setColor(cell.getBackground());
			g.fillRect(0
			if (commit != null)
				paintCommit(commit
		} finally {
			g.dispose();
			g = null;
		}
	}

	@Override
	protected void drawLine(final Color color
			int y2
		if (y1 == y2) {
			x1 -= width / 2;
			x2 -= width / 2;
		} else if (x1 == x2) {
			y1 -= width / 2;
			y2 -= width / 2;
		}

		g.setColor(color);
		g.setStroke(CommitGraphPane.stroke(width));
		g.drawLine(x1
	}

	@Override
	protected void drawCommitDot(final int x
			final int h) {
		g.setColor(Color.blue);
		g.setStroke(CommitGraphPane.strokeCache[1]);
		g.fillOval(x
		g.setColor(Color.black);
		g.drawOval(x
	}

	@Override
	protected void drawBoundaryDot(final int x
			final int h) {
		g.setColor(cell.getBackground());
		g.setStroke(CommitGraphPane.strokeCache[1]);
		g.fillOval(x
		g.setColor(Color.black);
		g.drawOval(x
	}

	@Override
	protected void drawText(String msg
		final int texth = g.getFontMetrics().getHeight();
		final int y0 = (y - texth) / 2 + (cell.getHeight() - texth) / 2;
		g.setColor(cell.getForeground());
		g.drawString(msg
	}

	@Override
	protected Color laneColor(SwingLane myLane) {
		return myLane != null ? myLane.color : Color.black;
	}

	void paintTriangleDown(int cx
		final int tipX = cx;
		final int tipY = y + h;
		final int baseX1 = cx - 10 / 2;
		final int baseX2 = tipX + 10 / 2;
		final int baseY = y;
		final Polygon triangle = new Polygon();
		triangle.addPoint(tipX
		triangle.addPoint(baseX1
		triangle.addPoint(baseX2
		g.fillPolygon(triangle);
		g.drawPolygon(triangle);
	}

	@Override
	protected int drawLabel(int x
		String txt;
		String name = ref.getName();
		if (name.startsWith(Constants.R_HEADS)) {
			g.setBackground(Color.GREEN);
			txt = name.substring(Constants.R_HEADS.length());
		} else if (name.startsWith(Constants.R_REMOTES)){
			g.setBackground(Color.LIGHT_GRAY);
			txt = name.substring(Constants.R_REMOTES.length());
		} else if (name.startsWith(Constants.R_TAGS)){
			g.setBackground(Color.YELLOW);
			txt = name.substring(Constants.R_TAGS.length());
		} else {
			g.setBackground(Color.WHITE);
			if (name.startsWith(Constants.R_REFS))
				txt = name.substring(Constants.R_REFS.length());
			else
		}
		if (ref.getPeeledObjectId() != null) {
			float[] colorComponents = g.getBackground().getRGBColorComponents(null);
			colorComponents[0] *= 0.9f;
			colorComponents[1] *= 0.9f;
			colorComponents[2] *= 0.9f;
			g.setBackground(new Color(colorComponents[0]
		}
		if (txt.length() > 12)
			txt = txt.substring(0

		final int texth = g.getFontMetrics().getHeight();
		int textw = g.getFontMetrics().stringWidth(txt);
		g.setColor(g.getBackground());
		int arcHeight = texth/4;
		int y0 = y - texth/2 + (cell.getHeight() - texth)/2;
		g.fillRoundRect(x 
		g.setColor(g.getColor().darker());
		g.drawRoundRect(x
		g.setColor(Color.BLACK);
		g.drawString(txt

		return arcHeight * 3 + textw;
	}
}
