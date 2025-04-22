
package org.eclipse.core.databinding.observable;

import java.util.function.Consumer;

import org.eclipse.core.internal.databinding.observable.CompositeSideEffect;

public interface ICompositeSideEffect extends ISideEffect, Consumer<ISideEffect> {

	static ICompositeSideEffect create() {
		return new CompositeSideEffect();
	}

	static ICompositeSideEffect create(Realm realm) {
		return new CompositeSideEffect(realm);
	}
}
