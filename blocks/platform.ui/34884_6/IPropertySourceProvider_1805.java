package org.eclipse.ui.views.properties;

public interface IPropertySource2 extends IPropertySource {

    boolean isPropertyResettable(Object id);

    @Override
	public boolean isPropertySet(Object id);
}
