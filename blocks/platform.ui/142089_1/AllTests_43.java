package org.eclipse.ui.tests.views.properties.tabbed.views;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

public class TypeMapper
    extends AbstractTypeMapper {

    @Override
	public Class mapType(Object object) {
        if (object instanceof TreeNode) {
            return ((TreeNode) object).getValue().getClass();
        }
        return super.mapType(object);
    }

}
