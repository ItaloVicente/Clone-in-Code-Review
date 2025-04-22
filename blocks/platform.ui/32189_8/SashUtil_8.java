
package org.eclipse.e4.ui.internal.workbench;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.e4.ui.model.application.MApplicationElement;
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

	private Double defValue = 10000.0;
	private Boolean bDefAbsolute = false;
	PartResizeMode resizeMode = PartResizeMode.WEIGHTED;
	private MApplicationElement uiElement = null;

	public static final String PREFIX = "partSizeInfo"; //$NON-NLS-1$
	public static final String KEY_SIZE = PREFIX + "." + "size"; //$NON-NLS-1$//$NON-NLS-2$
	public static final String KEY_RESIZE_MODE = PREFIX + "." + "resizeMode"; //$NON-NLS-1$//$NON-NLS-2$
	public static final String KEY_UNIT = PREFIX + "." + "unit"; //$NON-NLS-1$//$NON-NLS-2$

	public static final String KEY_TRANSIENT_DATA = PREFIX;

	public PartSizeInfo() {
	}

	public PartSizeInfo(MUIElement element) {
		uiElement = element;
		try {
			setDefaultValue(Double.parseDouble(element.getPersistedState().get(KEY_SIZE)));
			setResizeMode("fixed".equals(element.getPersistedState().get(KEY_RESIZE_MODE)) ? PartResizeMode.FIXED //$NON-NLS-1$
					: PartResizeMode.WEIGHTED);
			setDefaultAbsolute("px".equals(element.getPersistedState().get( //$NON-NLS-1$
					KEY_UNIT)));
		} catch (@SuppressWarnings("unused") Exception e) {
			if (element.getContainerData() != null && !element.getContainerData().isEmpty()) {
				PartSizeInfo info = parse(element.getContainerData());
				setDefaultValue(info.getDefaultValue());
				setDefaultAbsolute(info.isDefaultAbsolute());
				setResizeMode(info.getResizeMode());
				element.setContainerData(null);
			} else {
				setDefaultValue(10000.0);
				setDefaultAbsolute(false);
				setResizeMode(PartResizeMode.WEIGHTED);
			}
			storeInfo(element);
		}
	}

	public void storeInfo(MUIElement element) {
		Map<String, String> map = element.getPersistedState();
		map.put(KEY_SIZE, getDefaultValue().toString());
		map.put(KEY_UNIT, isDefaultAbsolute() ? "px" : ""); //TODO we could use '%' here. //$NON-NLS-1$ //$NON-NLS-2$
		map.put(KEY_RESIZE_MODE, isDefaultAbsolute() ? "fixed" : "weighted"); //$NON-NLS-1$ //$NON-NLS-2$
	}

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

	public String toParsableString() {
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

	public static PartSizeInfo get(MUIElement ele) {
		PartSizeInfo ret = (PartSizeInfo) ele.getTransientData().get(KEY_TRANSIENT_DATA);
		if (ret == null) {
			ret = new PartSizeInfo(ele);
		}
		return ret;
	}

	public static void copy(MUIElement target, MApplicationElement source) {
		PartSizeInfo info = get(target);
		if (info == null) {
			info = new PartSizeInfo(target);
			PartSizeInfo srcInfo;
			if (source instanceof MUIElement) {
				MUIElement element = (MUIElement) source;
				srcInfo = get(element);
				info.setDefaultValue(srcInfo.getDefaultValue());
				info.setDefaultAbsolute(srcInfo.isDefaultAbsolute());
				info.setResizeMode(srcInfo.getResizeMode());
			}
			info.storeInfo(target);
		}
	}

	public void set(double size, String unit, PartResizeMode resizeMode) {
		setDefaultValue(size);
		setDefaultAbsolute("fixed".equals(unit)); //$NON-NLS-1$
		setResizeMode(resizeMode);
	}

	public void notifyChanged() {
		if (uiElement != null) {
			uiElement.getTransientData().put(KEY_TRANSIENT_DATA, null);
			uiElement.getTransientData().put(KEY_TRANSIENT_DATA, this);
		}
	}

}
