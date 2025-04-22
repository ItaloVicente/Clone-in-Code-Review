package org.eclipse.ui.internal;


public interface IObjectContributor {
    public boolean isApplicableTo(Object object);

    public boolean canAdapt();
}
