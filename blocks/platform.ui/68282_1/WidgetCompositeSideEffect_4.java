
package org.eclipse.jface.databinding.observable;

import org.eclipse.core.databinding.observable.ICompositeSideEffect;
import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.jface.internal.databinding.observable.WidgetCompositeSideEffect;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

public interface WidgetSideEffectFactory {

	static ICompositeSideEffect createCompositeSideEffect(Widget disposableWidget) {
		return new WidgetCompositeSideEffect(disposableWidget);
	}
}
