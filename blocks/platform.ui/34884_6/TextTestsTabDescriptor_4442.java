
package org.eclipse.ui.tests.views.properties.tabbed.text;

import org.eclipse.jface.viewers.IFilter;

public class TextTestsSectionDescriptor2 extends TextTestsSectionDescriptor {

	public TextTestsSectionDescriptor2(String word, String tabId) {
		super(word, tabId);
	}

	public IFilter getFilter() {
		return new IFilter() {

			public boolean select(Object toTest) {
				return false;
			}
		};
	}

}
