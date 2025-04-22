package org.eclipse.ui.tests.views.properties.tabbed.views;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.model.Information;

public class InformationTwoFilter
    implements IFilter {

    @Override
	public boolean select(Object object) {
        if (object instanceof TreeNode) {
            Element element = (Element) ((TreeNode) object).getValue();
            if (element instanceof Information) {
                Information information = (Information) element;
                if (information.getName().contains("Two")) {//$NON-NLS-1$
                    return true;
                }
            }
        }
        return false;
    }

}
