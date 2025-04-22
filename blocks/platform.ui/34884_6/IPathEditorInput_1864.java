package org.eclipse.ui;

public interface IPartService {

    public void addPartListener(IPartListener listener);

    public void addPartListener(IPartListener2 listener);

    public IWorkbenchPart getActivePart();

    public IWorkbenchPartReference getActivePartReference();

    public void removePartListener(IPartListener listener);

    public void removePartListener(IPartListener2 listener);
}
