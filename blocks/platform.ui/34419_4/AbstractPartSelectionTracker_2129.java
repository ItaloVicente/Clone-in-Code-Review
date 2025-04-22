
package org.eclipse.ui.internal;

import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IEvaluationReference;
import org.eclipse.ui.services.IEvaluationService;

public abstract class AbstractEvaluationHandler extends AbstractEnabledHandler {
	private final static String PROP_ENABLED = "enabled"; //$NON-NLS-1$
	private IEvaluationService evaluationService;
	private IPropertyChangeListener enablementListener;
	private IEvaluationReference enablementRef;

	protected IEvaluationService getEvaluationService() {
		if (evaluationService == null) {
			evaluationService = PlatformUI.getWorkbench()
					.getService(IEvaluationService.class);
		}
		return evaluationService;
	}

	protected void registerEnablement() {
		enablementRef = getEvaluationService().addEvaluationListener(
				getEnabledWhenExpression(), getEnablementListener(),
				PROP_ENABLED);
	}

	protected abstract Expression getEnabledWhenExpression();

	private IPropertyChangeListener getEnablementListener() {
		if (enablementListener == null) {
			enablementListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty() == PROP_ENABLED) {
						if (event.getNewValue() instanceof Boolean) {
							setEnabled(((Boolean) event.getNewValue())
									.booleanValue());
						} else {
							setEnabled(false);
						}
					}
				}
			};
		}
		return enablementListener;
	}

	@Override
	public void dispose() {
		if (enablementRef != null) {
			evaluationService.removeEvaluationListener(enablementRef);
			enablementRef = null;
			enablementListener = null;
			evaluationService = null;
		}
		super.dispose();
	}
}
