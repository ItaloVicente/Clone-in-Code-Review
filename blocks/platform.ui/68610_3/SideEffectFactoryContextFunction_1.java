
package org.eclipse.e4.ui.databinding.factory;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;
import org.eclipse.e4.ui.databinding.addon.SideEffectAddon;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public final class MPartSideEffects {

	public static final String PART_USES_ISIDEEFFECT_FACTORY = "partUsesISideEffectFactory";

	private MPartSideEffects() {
	}

	public static ISideEffectFactory createFactory(MPart part) {
		ICompositeSideEffect compositeSideEffect = part.getContext().get(ICompositeSideEffect.class);
		if (compositeSideEffect == null) {
			ICompositeSideEffect newCompositeSideEffect = ICompositeSideEffect.create();
			part.getTags().add(PART_USES_ISIDEEFFECT_FACTORY);
			part.getContext().set(ICompositeSideEffect.class, newCompositeSideEffect);
			compositeSideEffect = newCompositeSideEffect;
		}
		return ISideEffectFactory.createFactory(compositeSideEffect::add);
	}

}
