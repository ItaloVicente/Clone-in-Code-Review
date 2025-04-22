package org.eclipse.ui.tests.api;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.ISaveablePart;

public class UserSaveableSharedViewPart extends MockViewPart implements
		ISaveablePart {
	public static String ID = "org.eclipse.ui.tests.api.UserSaveableSharedViewPart";

	public static class SharedModel {
		public boolean isDirty = true;
	}

	private SharedModel fSharedModel = new SharedModel();

	@Override
	public void doSave(IProgressMonitor monitor) {
		callTrace.add("doSave");
		fSharedModel.isDirty = false;
	}

	@Override
	public void doSaveAs() {
		callTrace.add("doSaveAs");
	}

	@Override
	public boolean isDirty() {
		callTrace.add("isDirty");
		return fSharedModel.isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		callTrace.add("isSaveOnCloseNeeded");
		return fSharedModel.isDirty;
	}

	public void setSharedModel(SharedModel s) {
		fSharedModel = s;
	}

	public SharedModel getSharedModel() {
		return fSharedModel;
	}
}
