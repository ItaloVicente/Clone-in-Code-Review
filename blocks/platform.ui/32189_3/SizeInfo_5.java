
package org.eclipse.e4.ui.workbench.renderers.swt;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.ui.model.application.ui.MGenericTile;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

public class SashUtil {

	static public List<MUIElement> getVisibleChildren(
			MGenericTile<?> sashContainer) {
		List<MUIElement> visKids = new ArrayList<MUIElement>();
		for (MUIElement child : sashContainer.getChildren()) {
			if (child.isToBeRendered() && child.isVisible())
				visKids.add(child);
		}
		return visKids;
	}

	static public void validateContainerData(MGenericTile<?> sashContainer) {
		boolean foundNoMax = false;
		MUIElement lastRelative = null;

		List<MUIElement> visibleChildren = getVisibleChildren(sashContainer);
		for (MUIElement ele : visibleChildren) {
			SizeInfo sizeInfo = SizeInfo.parse(ele.getContainerData());
			if (!sizeInfo.isDefaultAbsolute()) {
				lastRelative = ele;
				if (sizeInfo.getMaxValue() == null) {
					foundNoMax = true;
				}
			}
		}
		if (lastRelative == null && visibleChildren.isEmpty() == false) {
			lastRelative = visibleChildren.get(visibleChildren.size() - 1);
			lastRelative.setContainerData("10000%"); //$NON-NLS-1$
		}

		if (foundNoMax == false && lastRelative != null) {
			SizeInfo info = SizeInfo.parse(lastRelative.getContainerData());
			info.setMaxValue(null);
			lastRelative.setContainerData(info.getEncodedParameters());

		}
	}

	static public double getTotalWeight(List<MUIElement> visibleChildren) {
		double total = 0;
		for (MUIElement item : visibleChildren) {
			SizeInfo info = SizeInfo.parse(item.getContainerData());
			if (!info.isDefaultAbsolute()) {
				total += info.getDefaultValue();
			}
		}
		return total;
	}

	static public int getAvailableRelative(boolean isHorizontal, int totalSize,
			int sashWidth, List<MUIElement> visibleChildren) {
		int availableRelative;
		int totalFixed = 0;
		for (MUIElement item : visibleChildren) {
			SizeInfo info = SizeInfo.parse(item.getContainerData());
			if (info.isDefaultAbsolute()) {
				totalFixed += info.getValueConstrained(0, 0);
			}
		}
		int sashSpace = (visibleChildren.size() - 1) * sashWidth;
		if (isHorizontal) {
			availableRelative = totalSize - totalFixed - sashSpace;
		} else {
			availableRelative = totalSize - totalFixed - sashSpace;
		}
		return availableRelative;
	}

}
