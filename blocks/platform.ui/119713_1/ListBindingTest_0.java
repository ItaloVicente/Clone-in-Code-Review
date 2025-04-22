package org.eclipse.core.databinding;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class UpdateListStrategy2 extends UpdateListStrategy {
	public UpdateListStrategy2() {
		this(true, POLICY_UPDATE);
	}

	public UpdateListStrategy2(int updatePolicy) {
		this(true, updatePolicy);
	}

	@SuppressWarnings("deprecation")
	public UpdateListStrategy2(boolean provideDefaults, int updatePolicy) {
		super(provideDefaults, updatePolicy);
	}

	@Override
	protected boolean useMoveAndReplace() {
		return getClass() == UpdateListStrategy2.class;
	}
}
