
package org.eclipse.e4.ui.workbench.addons.minmax;

import java.util.LinkedHashMap;
import java.util.Map;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

public class TrimStackIdHelper {

	private static final String ID_SUFFIX = "(minimized)"; //$NON-NLS-1$

	public enum TrimStackIdPart {
		ELEMENT_ID, /**
		WINDOW_ID, /**
		PERSPECTIVE_ID
	}

	private TrimStackIdHelper() {
	}

	public static Map<TrimStackIdPart, String> parseTrimStackId(String trimStackId) {
		int index = trimStackId.indexOf('(');
		String stackId = trimStackId.substring(0, index);
		Map<TrimStackIdPart, String> result = new LinkedHashMap<TrimStackIdHelper.TrimStackIdPart, String>();
		result.put(TrimStackIdPart.ELEMENT_ID, stackId);
		String suffix = trimStackId.substring(index);
		if (ID_SUFFIX.equalsIgnoreCase(suffix))
			return result;
		String windowPerspId = suffix.substring(1, suffix.length() - 1);
		int windowIdEnd = windowPerspId.indexOf(')');
		if (windowIdEnd != -1) {
			String windowId = windowPerspId.substring(0, windowIdEnd);
			String perspId = windowPerspId.substring(windowPerspId.indexOf('(') + 1, windowPerspId.length());
			result.put(TrimStackIdPart.WINDOW_ID, windowId);
			result.put(TrimStackIdPart.PERSPECTIVE_ID, perspId);
		} else {
			result.put(TrimStackIdPart.PERSPECTIVE_ID, windowPerspId);
		}
		return result;
	}

	public static String createTrimStackId(MUIElement element,MPerspective perspective,MWindow window){
		StringBuilder sb = new StringBuilder(element.getElementId());
		if (perspective == null) {
			sb.append(ID_SUFFIX);
		} else {
			if (window != null && window.getElementId() != null)
				sb.append('(' + window.getElementId() + ')' + '.');
			if (perspective.getElementId() != null)
				sb.append('(' + perspective.getElementId() + ')');
		}
		return sb.toString();
	}
}
