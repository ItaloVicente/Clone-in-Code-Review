
package org.eclipse.core.databinding.observable;

public interface ISideEffect {
	void dispose();

	void pause();

	void resume();

	void resumeAndRunIfDirty();

	void runIfDirty();
}
