
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.ISaveablePart;

public interface ISecondarySaveableSource {

	default boolean isDirtyStateSupported() {
		return false;
	}
}
