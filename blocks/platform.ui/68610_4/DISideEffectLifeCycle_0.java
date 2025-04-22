package org.eclipse.e4.ui.databinding.internal;

import org.eclipse.core.databinding.observable.sideeffect.CompositeSideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.osgi.service.component.annotations.Component;

@Component(service = IContextFunction.class, property = "service.context.key=org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory")
public class DISideEffectContextFunction implements IContextFunction {

	public static final String PART_USES_ISIDEEFFECT_FACTORY = "partUsesISideEffectFactory";

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		MPart part = context.get(MPart.class);
		if (null == part) {
			throw new IllegalStateException("The ISideEffectFactory is only supposed to be injected into MParts");
		}

		ISideEffectFactory sideEffectFactory = getFactory(part);

		return sideEffectFactory;
	}

	public static ISideEffectFactory getFactory(MPart part) {
		CompositeSideEffect compositeSideEffect = part.getContext().get(CompositeSideEffect.class);
		if (compositeSideEffect == null) {
			CompositeSideEffect newCompositeSideEffect = new CompositeSideEffect();
			part.getTags().add(PART_USES_ISIDEEFFECT_FACTORY);
			part.getContext().set(CompositeSideEffect.class, newCompositeSideEffect);
			ContextInjectionFactory.make(DISideEffectLifeCycle.class, part.getContext());

			compositeSideEffect = newCompositeSideEffect;
		}
		return ISideEffectFactory.createFactory(compositeSideEffect::add);
	}

}
