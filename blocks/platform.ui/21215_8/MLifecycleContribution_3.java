package org.eclipse.e4.ui.model.application;

import java.util.List;

public interface MLifecycleAware {
	List<MLifecycleContribution> getLifeCycleHandlers();

} // MLifecycleAware
