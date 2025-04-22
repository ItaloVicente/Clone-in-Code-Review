package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;


public abstract class AbstractStringPropertySection
	extends AbstractTextPropertySection {
	
	protected boolean isEqual(String newText) {
		return getFeatureAsText().equals(newText);
	}
	
	protected String getFeatureAsText() {
		String string = (String)eObject.eGet(getFeature());
		if (string == null) {
			return "";//$NON-NLS-1$
		}
		return string;
	}
	
	protected Object getFeatureValue(String newText) {
		return newText;
	}
}
