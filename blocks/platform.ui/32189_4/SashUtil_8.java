
package org.eclipse.e4.ui.internal.workbench;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.e4.ui.model.application.ui.MGenericTile;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;

public class PartSizeInfo {

	public enum PartResizeMode {
		FIXED,
		WEIGHTED
	}

	private static final String PX = "px"; //$NON-NLS-1$
	static final Pattern patternSizeValue = Pattern.compile("(\\d+\\.?\\d*)(px|%)?"); //$NON-NLS-1$
	static final Pattern patternSplit = Pattern.compile("\\s*;[;\\s]*"); //$NON-NLS-1$


	private Double defValue;
	private Boolean bDefAbsolute;
	PartResizeMode resizeMode = PartResizeMode.WEIGHTED;

	public void setResizeMode(PartResizeMode sizeMode) {
		this.resizeMode = sizeMode;
	}

	public static PartSizeInfo parse(String containerData) {
		PartSizeInfo ret = new PartSizeInfo();
		if (containerData == null) {
			containerData = ""; //$NON-NLS-1$
		}
		String[] pairs = patternSplit.split(containerData);
		for (String pair : pairs) {
			if (pair.equals("fixed")) { //$NON-NLS-1$
				ret.resizeMode = PartResizeMode.FIXED;
			} else if (pair.equals("weighted")) { //$NON-NLS-1$
				ret.resizeMode = PartResizeMode.WEIGHTED;
			} else {
				Matcher matcher = PartSizeInfo.patternSizeValue.matcher(pair);
				if (matcher.matches()) {
					ret.defValue = Double.parseDouble(matcher.group(1));
					ret.bDefAbsolute = PX.equals(matcher.group(2));
				}
			}
		}
		if (ret.defValue == null) {
			ret.bDefAbsolute = false;
			ret.defValue = 10000.0;
		}
		return ret;
	}

	public Double getValueConstrained(double totalRelative, double availableRelative) {
		return getDefaultValue();
	}

	public String getEncodedParameters() {
		String val = ""; //$NON-NLS-1$
		val += resizeMode == PartResizeMode.FIXED ? "fixed;" : "weighted;"; //$NON-NLS-1$ //$NON-NLS-2$
		if (defValue != null) {
			val += defValue;
			val += bDefAbsolute ? PX : ""; //$NON-NLS-1$
		}
		return val;
	}



	public double getValueAsAbsolute(double totalRelative, double availableRelative) {
		if (getDefaultValue() == null) {
			return 10000.0;
		} else if (isDefaultAbsolute()) {
			return getDefaultValue();
		} else {
			return getDefaultValue() / totalRelative * availableRelative;
		}
	}

	public void setDefaultAbsolute(boolean bIsAbsolute) {
		bDefAbsolute = bIsAbsolute;
	}

	public void setDefaultValue(double value) {
		defValue = value;
	}







	public Double getDefaultValue() {
		return defValue;
	}

	public Boolean isDefaultAbsolute() {
		return bDefAbsolute;
	}

	public PartResizeMode getResizeMode() {
		return resizeMode;
	}

	public void convertToWeighted(MUIElement element, int size) {
		MUIElement sashContainer = element;
		while (sashContainer != null && sashContainer instanceof MPartSashContainer == false) {
			sashContainer = sashContainer.getParent();
		}
		if (sashContainer == null) {
			return;
		}
		List<MUIElement> children = SashUtil.getVisibleChildren((MGenericTile<?>) sashContainer);
		Double pixels = getDefaultValue();
		setResizeMode(PartResizeMode.WEIGHTED);
		setDefaultValue(SashUtil.fixed2Weighted(pixels, children, (double) size, 4.0));
		setDefaultAbsolute(false);
	}
}
