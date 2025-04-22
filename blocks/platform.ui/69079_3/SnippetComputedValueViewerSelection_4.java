
package org.eclipse.jface.databinding.swt;

import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

public final class WidgetSideEffects {

	private WidgetSideEffects() {
	}

	public static ISideEffectFactory createFactory(Widget disposableWidget) {
		ICompositeSideEffect compositeSideEffect = (ICompositeSideEffect) disposableWidget
				.getData(ICompositeSideEffect.class.getName());
		if (compositeSideEffect == null) {
			ICompositeSideEffect newCompositeSideEffect = ICompositeSideEffect.create();
			disposableWidget.setData(ICompositeSideEffect.class.getName(), newCompositeSideEffect);
			disposableWidget.addDisposeListener(e -> newCompositeSideEffect.dispose());
			compositeSideEffect = newCompositeSideEffect;
		}
		return ISideEffectFactory.createFactory(compositeSideEffect::add);
	}
}
