
package org.eclipse.e4.ui.databinding.addon;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class SideEffectAddon {

	@Inject
	@Optional
	public void disposeSideEffectsOnPartDisposal(@EventTopic(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (element instanceof MPart && newValue == null) {
			MPart part = (MPart) element;

			ICompositeSideEffect compositeSideEffect = part.getContext().getLocal(ICompositeSideEffect.class);
			if (compositeSideEffect != null) {
				compositeSideEffect.dispose();
			}
		}
	}

	@Inject
	@Optional
	public void pasueAndResumeSideEffects(@EventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {

		Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);

		if (oldValue instanceof MPart) {
			MPart part = (MPart) oldValue;

			ICompositeSideEffect compositeSideEffect = part.getContext().getLocal(ICompositeSideEffect.class);
			if (compositeSideEffect != null) {
				compositeSideEffect.pause();
			}
		}

		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (newValue instanceof MPart) {
			MPart part = (MPart) newValue;

			ICompositeSideEffect compositeSideEffect = part.getContext().getLocal(ICompositeSideEffect.class);
			if (compositeSideEffect != null) {
				compositeSideEffect.resumeAndRunIfDirty();
			}
		}
	}

}
