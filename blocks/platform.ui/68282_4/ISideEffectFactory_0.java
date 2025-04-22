
package org.eclipse.core.databinding.observable;

import org.eclipse.core.internal.databinding.observable.CompositeSideEffect;

public interface ICompositeSideEffect extends ISideEffect {

	void add(ISideEffect sideEffect);

	void remove(ISideEffect sideEffect);

	static ICompositeSideEffect create() {
		return new CompositeSideEffect();
	}

}
