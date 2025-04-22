
package org.eclipse.e4.ui.databinding.factory;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public final class MPartSideEffects {

	private MPartSideEffects() {
	}

	public static ISideEffectFactory createFactory(MPart part) {
		ICompositeSideEffect compositeSideEffect = part.getContext().get(ICompositeSideEffect.class);
		if (compositeSideEffect == null) {
			ICompositeSideEffect newCompositeSideEffect = ICompositeSideEffect.create();
			part.getContext().set(ICompositeSideEffect.class, newCompositeSideEffect);
			compositeSideEffect = newCompositeSideEffect;
		}
		return ISideEffectFactory.createFactory(compositeSideEffect::add);
	}

}
