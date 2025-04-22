
package org.eclipse.e4.ui.tests.workbench;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Optional;

public class ImperativeExpressionTestEvaluation {
	@Evaluate
	public boolean isVisible(@Optional @Named("mmc1") boolean mmc1) {
		return mmc1;
	}
}
