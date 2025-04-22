package org.eclipse.egit.ui.internal.decorators;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

public interface IProblemDecoratable {

	public static int SEVERITY_NONE = -1;

	int getProblemSeverity();
}
