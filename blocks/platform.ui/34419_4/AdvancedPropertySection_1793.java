package org.eclipse.ui.views.properties.tabbed;

public class AbstractTypeMapper
    implements ITypeMapper {

    public Class mapType(Object object) {
        return object.getClass();
    }

}
