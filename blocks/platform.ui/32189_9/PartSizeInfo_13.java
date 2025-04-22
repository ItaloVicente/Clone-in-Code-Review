
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.ui.workbench.PartSizeInfo;
import org.eclipse.e4.ui.workbench.PartSizeInfo.PartResizeMode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.eclipse.e4.ui.model.application.ui.MGenericTile;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

public class SashUtil {

	static public List<MUIElement> getVisibleChildren(MGenericTile<?> sashContainer) {
		List<MUIElement> visKids = new ArrayList<MUIElement>();
		for (MUIElement child : sashContainer.getChildren()) {
			if (child.isToBeRendered() && child.isVisible())
				visKids.add(child);
		}
		return visKids;
	}

	static public void validatePartSize(MGenericTile<?> sashContainer, int size, double sashSize) {
		MUIElement lastRelative = null;
		List<MUIElement> visibleChildren = getVisibleChildren(sashContainer);

		if (size > 0) {
			double totalWeight = getTotalWeightForAllModes(visibleChildren);
			for (MUIElement ele : visibleChildren) {
				PartSizeInfo sizeInfo = PartSizeInfo.get(ele);
				if (sizeInfo.getResizeMode() == PartResizeMode.FIXED) {
					if (sizeInfo.isDefaultAbsolute() == false) {
						double pixels = sizeInfo.getDefaultValue()
								/ totalWeight
										visibleChildren);
						if (pixels < 10) {
							pixels = 0;
						}
						sizeInfo.setDefaultAbsolute(true);
						sizeInfo.setDefaultValue(pixels);
						sizeInfo.notifyChanged();
					}
				}
			}

			Hashtable<MUIElement, Double> map = new Hashtable<MUIElement, Double>();
			for (MUIElement ele : visibleChildren) {
				PartSizeInfo sizeInfo = PartSizeInfo.get(ele);
				if (sizeInfo.getResizeMode() == PartResizeMode.WEIGHTED) {
					if (sizeInfo.isDefaultAbsolute()) {
						double pixelsRequested = sizeInfo.getDefaultValue();

						double totalRelative = getTotalWeight(visibleChildren);
						if (totalRelative == 0) {
							totalRelative = 10000;
						}
						double totalFixupPixels = getTotalFixedPixelsForWeightModeElements(visibleChildren);

						double availablePixels = size - getTotalFixedPixels(visibleChildren)
								- ((visibleChildren.size() - 1) * sashSize);

						double weightForAbsoluteSpecifiedRelativeContainers = (totalFixupPixels * totalRelative)
								/ (availablePixels - totalFixupPixels);

						double ratio = pixelsRequested / totalFixupPixels;
						double newWeight = ratio * weightForAbsoluteSpecifiedRelativeContainers;
						map.put(ele, newWeight);
					}
				}
			}
			for (MUIElement element : map.keySet()) {
				PartSizeInfo sizeInfo = PartSizeInfo.get(element);
				sizeInfo.setDefaultAbsolute(false);
				sizeInfo.setDefaultValue(map.get(element));
				sizeInfo.notifyChanged();
			}
		}

		for (MUIElement ele : visibleChildren) {
			PartSizeInfo sizeInfo = PartSizeInfo.get(ele);
			if (sizeInfo.getResizeMode() == PartResizeMode.WEIGHTED) {
				lastRelative = ele;
			}
		}
		if (lastRelative == null && visibleChildren.isEmpty() == false) {
			lastRelative = visibleChildren.get(visibleChildren.size() - 1);
			PartSizeInfo info = PartSizeInfo.get(lastRelative);
			info.setResizeMode(PartResizeMode.WEIGHTED);
			info.setDefaultAbsolute(false);
			info.setDefaultValue(5000);
			info.storeInfo();
			info.notifyChanged();
		}
	}

	static public double getTotalWeight(List<MUIElement> visibleChildren) {
		double total = 0;
		for (MUIElement item : visibleChildren) {
			PartSizeInfo info = PartSizeInfo.get(item);
			if (info.getResizeMode() == PartResizeMode.WEIGHTED) {
				if (info.isDefaultAbsolute()) {
				} else {
					total += info.getDefaultValue();
				}
			}
		}
		return total;
	}

	static public double getTotalWeightForAllModes(List<MUIElement> visibleChildren) {
		double total = 0;
		for (MUIElement item : visibleChildren) {
			PartSizeInfo info = PartSizeInfo.get(item);
			if (!info.isDefaultAbsolute()) {
				total += info.getDefaultValue();
			}
		}
		return total;
	}

	static public int getTotalFixedPixelsForWeightModeElements(List<MUIElement> visibleChildren) {
		int totalFixed = 0;
		for (MUIElement item : visibleChildren) {
			PartSizeInfo info = PartSizeInfo.get(item);
			if (info.getResizeMode() == PartResizeMode.WEIGHTED) {
				if (info.isDefaultAbsolute() == true) {
					totalFixed += info.getDefaultValue();
				}
			}
		}
		return totalFixed;
	}

	static public int getTotalFixedPixels(List<MUIElement> visibleChildren) {
		int totalFixed = 0;
		for (MUIElement item : visibleChildren) {
			PartSizeInfo info = PartSizeInfo.get(item);
			if (info.getResizeMode() == PartResizeMode.FIXED) {
				if (info.isDefaultAbsolute() == true) {
					totalFixed += info.getDefaultValue();
				}
			}
		}
		return totalFixed;
	}

	static public int getAvailableRelative(boolean isHorizontal, int totalSize, int sashWidth,
			List<MUIElement> visibleChildren) {
		int availableRelative;
		int totalFixed = 0;
		for (MUIElement item : visibleChildren) {
			PartSizeInfo info = PartSizeInfo.get(item);
			if (info.getResizeMode() == PartResizeMode.FIXED) {
				if (info.isDefaultAbsolute() == true) {
					totalFixed += info.getDefaultValue();
				}
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

	static public double fixed2Weighted(Double pixelsRequested, List<MUIElement> visibleChildren,
			Double size, Double sashSize) {
		double totalRelative = getTotalWeight(visibleChildren);
		if (totalRelative == 0) {
			totalRelative = 10000;
		}

		double availablePixels = size - (getTotalFixedPixels(visibleChildren) - pixelsRequested)
				- ((visibleChildren.size() - 1) * sashSize);

		double weightForAbsoluteSpecifiedRelativeContainers = (pixelsRequested * totalRelative)
				/ (availablePixels - pixelsRequested);

		double newWeight = weightForAbsoluteSpecifiedRelativeContainers;
		return newWeight;
	}

}
