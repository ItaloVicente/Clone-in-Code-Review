package org.eclipse.ui.tests.views.properties.tabbed.text;

import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class TextTestsTabDescriptor extends AbstractTabDescriptor {

	private String word;

	public TextTestsTabDescriptor(String aWord) {
		super();
		this.word = aWord;
		getSectionDescriptors().add(new TextTestsSectionDescriptor(aWord, getId()));
		getSectionDescriptors().add(new TextTestsSectionDescriptor2(aWord, getId()));
	}

	public String getCategory() {
		return "default"; //$NON-NLS-1$
	}

	public String getId() {
		return word + "@" + Integer.toHexString(word.hashCode());
	}

	public String getLabel() {
		return word;
	}
}
