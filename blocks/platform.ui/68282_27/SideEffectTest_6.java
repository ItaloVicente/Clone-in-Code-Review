
package org.eclipse.jface.databinding.swt;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.CompositeSideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

public final class WidgetSideEffects {

	public static ISideEffectFactory createFactory(Widget disposableWidget) {
		CompositeSideEffect compositeSideEffect = (CompositeSideEffect) disposableWidget
				.getData(CompositeSideEffect.class.getName());
		if (compositeSideEffect == null) {
			CompositeSideEffect newCompositeSideEffect = new CompositeSideEffect();
			disposableWidget.setData(CompositeSideEffect.class.getName(), newCompositeSideEffect);
			disposableWidget.addDisposeListener(e -> newCompositeSideEffect.dispose());
			compositeSideEffect = newCompositeSideEffect;
		}
		return ISideEffectFactory.createFactory(compositeSideEffect::add);
	}

	private WidgetSideEffects() {
	}
}
