package org.eclipse.core.databinding.bind;

import org.eclipse.core.runtime.IStatus;

public interface ITargetBinding<F> {
	void setTargetValue(F targetValue);

	void setStatus(IStatus status);
}
