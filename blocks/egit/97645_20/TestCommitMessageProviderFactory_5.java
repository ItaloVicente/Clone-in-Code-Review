package org.eclipse.egit.ui.test.stagview;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class TestCommitMessageProviderExtensionFactory2
		implements IExecutableExtensionFactory {

	protected static final TestCommitMessageProviderFactory INSTANCE = new TestCommitMessageProviderFactory();

	@Override
	public Object create() throws CoreException {
		return INSTANCE.create();
	}

}
