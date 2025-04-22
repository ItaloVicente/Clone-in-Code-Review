
package org.eclipse.e4.ui.tests.workbench;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.model.application.ui.MImperativeExpression;

public class ImperativeExpressionTestEvaluationPersistedState {
	public static final String PERSISTED_STATE_TEST = "persisted-state-test";

	@Evaluate
	public boolean isVisible(MImperativeExpression exp) {
		return exp.getPersistedState().containsKey(PERSISTED_STATE_TEST);
	}
}
