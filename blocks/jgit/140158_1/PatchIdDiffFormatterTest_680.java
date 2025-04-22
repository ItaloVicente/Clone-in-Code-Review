
package org.eclipse.jgit.diff;

public class MyersDiffTest extends AbstractDiffTestCase {
	@Override
	protected DiffAlgorithm algorithm() {
		return MyersDiff.INSTANCE;
	}
}
