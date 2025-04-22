
package org.eclipse.e4.ui.workbench.modeling;

public interface IModelFragmentContribution {

	default String getUri() {
		return "fragment.e4xmi"; //$NON-NLS-1$
	}

	default ApplyAttribute getApplyAttribute() {
		return ApplyAttribute.ALWAYS;
	}
}
