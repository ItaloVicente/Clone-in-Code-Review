
package org.eclipse.jgit.awtui;

import java.awt.Color;
import java.util.LinkedList;

import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;

class SwingCommitList extends PlotCommitList<SwingCommitList.SwingLane> {
	final LinkedList<Color> colors;

	SwingCommitList() {
		colors = new LinkedList<>();
		repackColors();
	}

	private void repackColors() {
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.red);
		colors.add(Color.magenta);
		colors.add(Color.darkGray);
		colors.add(Color.yellow.darker());
		colors.add(Color.orange);
	}

	@Override
	protected SwingLane createLane() {
		final SwingLane lane = new SwingLane();
		if (colors.isEmpty())
			repackColors();
		lane.color = colors.removeFirst();
		return lane;
	}

	@Override
	protected void recycleLane(SwingLane lane) {
		colors.add(lane.color);
	}

	static class SwingLane extends PlotLane {
		private static final long serialVersionUID = 1L;
		Color color;
	}
}
