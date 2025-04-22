package org.eclipse.ui.tests.navigator.m12.model;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
public class M1Core {
	public static M1Resource getModelObject(IResource res) {
		switch (res.getType()) {
		case IResource.PROJECT:
			return new M1Project((IProject) res);
		case IResource.FOLDER:
			return new M1Folder((IFolder) res);
		case IResource.FILE:
			return new M1File((IFile) res);
		default:
			return null;
		}
	}
}
