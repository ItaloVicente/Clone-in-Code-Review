
package org.eclipse.e4.ui.internal.workbench;

public class TagsUtil {
	public static boolean isTagAdded(Object oldValue, Object newValue, String tagName) {
		return oldValue == null && tagName.equals(newValue);
	}

	public static boolean isTagRemoved(Object oldValue, Object newValue, String tagName) {
		return newValue == null && tagName.equals(oldValue);
	}

	public static boolean isTagModified(Object oldValue, Object newValue, String tagName) {
		return isTagAdded(oldValue, newValue, tagName) || isTagRemoved(oldValue, newValue, tagName);
	}
}
