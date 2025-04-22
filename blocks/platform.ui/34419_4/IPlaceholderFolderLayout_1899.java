package org.eclipse.ui;

public interface IPerspectiveRegistry {
    public IPerspectiveDescriptor clonePerspective(String id, String label,
            IPerspectiveDescriptor desc) throws IllegalArgumentException;

	public void deletePerspective(IPerspectiveDescriptor persp);
    
    public IPerspectiveDescriptor findPerspectiveWithId(String perspectiveId);

    public IPerspectiveDescriptor findPerspectiveWithLabel(String label);

    public String getDefaultPerspective();

    public IPerspectiveDescriptor[] getPerspectives();

    public void setDefaultPerspective(String id);

    public void revertPerspective(IPerspectiveDescriptor perspToRevert);
}
