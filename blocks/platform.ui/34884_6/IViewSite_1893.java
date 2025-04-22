
package org.eclipse.ui;

public interface IViewReference extends IWorkbenchPartReference {

    public String getSecondaryId();

    public IViewPart getView(boolean restore);

    public boolean isFastView();
}
