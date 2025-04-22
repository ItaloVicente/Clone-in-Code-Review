package org.eclipse.ui.tests.navigator.m12.model;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.tests.navigator.m12.model.M1Container;
public class M1Project extends M1Container implements IAdaptable {
	public M1Project(IProject project) {
		super(project);
	}
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
}
