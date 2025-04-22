package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

public abstract class AbstractIntegerPropertySection
	extends AbstractTextPropertySection {

	protected boolean isEqual(String newText) {
		try {
			Integer.parseInt(newText);
		} catch (NumberFormatException e) {
			refresh();
			return true;
		}
		Integer integer = new Integer(Integer.parseInt(newText)); 
		return getFeatureInteger().equals(integer);
	}

	protected String getFeatureAsText() {
		return getFeatureInteger().toString();
	}

	protected abstract Integer getFeatureInteger();

	protected Object getFeatureValue(String newText) {
		return new Integer(Integer.parseInt(newText));
	}
}
