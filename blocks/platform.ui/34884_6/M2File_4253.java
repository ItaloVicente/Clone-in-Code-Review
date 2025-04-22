package org.eclipse.ui.tests.navigator.m12.model;
public class M2Core {
	public static M2Resource getModelObject(Object object) {
		if (object instanceof M1File) {
			M1File file = (M1File) object;
			if (file.getResource().getName().indexOf("2") >= 0) {
				return new M2File(file.getResource());
			}
		}
		return null;
	}
}
