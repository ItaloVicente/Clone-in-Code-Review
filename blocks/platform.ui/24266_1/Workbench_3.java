package org.eclipse.e4.ui.internal.css.swt.preference;

public interface IPreferenceNodeOverridable {
	String getId();

	void overridePreference(String name, String value);
}
