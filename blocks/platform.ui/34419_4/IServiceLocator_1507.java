
package org.eclipse.ui.services;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.ISources;

public interface IEvaluationService extends IServiceWithSources {
	public static final String RESULT = "org.eclipse.ui.services.result"; //$NON-NLS-1$

	public static final String PROP_NOTIFYING = "org.eclipse.ui.services.notifying"; //$NON-NLS-1$

	public void addServiceListener(IPropertyChangeListener listener);

	public void removeServiceListener(IPropertyChangeListener listener);

	public IEvaluationReference addEvaluationListener(Expression expression,
			IPropertyChangeListener listener, String property);

	public void addEvaluationReference(IEvaluationReference ref);

	public void removeEvaluationListener(IEvaluationReference ref);

	public IEvaluationContext getCurrentState();

	public void requestEvaluation(String propertyName);
}
