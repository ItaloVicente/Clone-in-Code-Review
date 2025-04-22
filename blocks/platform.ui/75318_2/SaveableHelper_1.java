
package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;

public interface ISecondarySaveableSource {

	default boolean isDirtyStateIndicationSupported() {
		return false;
	}
}
