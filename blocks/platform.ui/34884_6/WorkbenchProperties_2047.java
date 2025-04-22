
package org.eclipse.ui.databinding;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.services.IServiceLocator;

public class WorkbenchObservables {
	public static IObservableValue observeDetailAdaptedValue(
			IObservableValue master, Class adapter) {
		return observeDetailAdaptedValue(master, adapter, Platform
				.getAdapterManager());
	}

	static IObservableValue observeDetailAdaptedValue(
			IObservableValue master, Class adapter,
			IAdapterManager adapterManager) {
		return WorkbenchProperties.adaptedValue(adapter, adapterManager)
				.observeDetail(master);
	}

	public static IObservableValue observeAdaptedSingleSelection(
			IServiceLocator locator, Class targetType) {
		ISelectionService selectionService = locator
				.getService(ISelectionService.class);
		Assert.isNotNull(selectionService);
		return WorkbenchProperties.singleSelection(null, true).value(
				WorkbenchProperties.adaptedValue(targetType)).observe(
				selectionService);
	}
}
