
package org.eclipse.ui.internal.services;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.services.IEvaluationReference;
import org.eclipse.ui.services.IEvaluationService;

public class SlaveEvaluationService implements IEvaluationService {

	private IEvaluationService parentService;

	private Collection sourceProviders = new ArrayList();

	private Collection serviceListeners = new ArrayList();

	private Collection evaluationReferences = new ArrayList();

	public SlaveEvaluationService(IEvaluationService parent) {
		parentService = parent;
	}

	@Override
	public IEvaluationReference addEvaluationListener(Expression expression,
			IPropertyChangeListener listener, String property) {
		IEvaluationReference ref = parentService.addEvaluationListener(
				expression, listener, property);
		if (!evaluationReferences.contains(ref)) {
			evaluationReferences.add(ref);
		}
		return ref;
	}
	
	@Override
	public void addEvaluationReference(IEvaluationReference ref) {
		if (!evaluationReferences.contains(ref)) {
			evaluationReferences.add(ref);
		}
		parentService.addEvaluationReference(ref);
	}

	@Override
	public void addServiceListener(IPropertyChangeListener listener) {
		if (!serviceListeners.contains(listener)) {
			serviceListeners.add(listener);
		}
		parentService.addServiceListener(listener);
	}

	@Override
	public void addSourceProvider(ISourceProvider provider) {
		if (!sourceProviders.contains(provider)) {
			sourceProviders.add(provider);
		}
		parentService.addSourceProvider(provider);
	}

	@Override
	public IEvaluationContext getCurrentState() {
		return parentService.getCurrentState();
	}

	@Override
	public void removeEvaluationListener(IEvaluationReference ref) {
		evaluationReferences.remove(ref);
		parentService.removeEvaluationListener(ref);
	}

	@Override
	public void removeServiceListener(IPropertyChangeListener listener) {
		serviceListeners.remove(listener);
		parentService.removeServiceListener(listener);
	}

	@Override
	public void removeSourceProvider(ISourceProvider provider) {
		sourceProviders.remove(provider);
		parentService.removeSourceProvider(provider);
	}

	@Override
	public void dispose() {
		if (!evaluationReferences.isEmpty()) {
			Object[] array = evaluationReferences.toArray();
			for (int i = 0; i < array.length; i++) {
				parentService
						.removeEvaluationListener((IEvaluationReference) array[i]);
			}
		}
		if (!serviceListeners.isEmpty()) {
			Object[] array = serviceListeners.toArray();
			for (int i = 0; i < array.length; i++) {
				parentService
						.removeServiceListener((IPropertyChangeListener) array[i]);
			}
			serviceListeners.clear();
		}
		if (!sourceProviders.isEmpty()) {
			Object[] array = sourceProviders.toArray();
			for (int i = 0; i < array.length; i++) {
				parentService.removeSourceProvider((ISourceProvider) array[i]);
			}
			sourceProviders.clear();
		}
	}

	@Override
	public void requestEvaluation(String propertyName) {
		parentService.requestEvaluation(propertyName);
	}
}
