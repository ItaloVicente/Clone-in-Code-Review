
package org.eclipse.core.databinding.observable.sideeffect;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.internal.databinding.observable.sideeffect.CompositeSideEffect;

public interface ICompositeSideEffect extends ISideEffect {

	void add(ISideEffect sideEffect);

	void remove(ISideEffect sideEffect);

	static ICompositeSideEffect create() {
		return new CompositeSideEffect();
	}

}
