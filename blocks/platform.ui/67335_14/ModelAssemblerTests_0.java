
package org.eclipse.e4.ui.tests.workbench;

import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.fragment.MModelFragments;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;

public class ModelAssemblerProcessingOrderPostProcessor {

	@Execute
	public void run(EModelService eModelService, MApplication application) throws Exception {
		List<MPart> elements = eModelService.findElements(application, null, MPart.class,
				null);
		for (MPart element : elements) {
			if (((EObject) element).eContainer() instanceof MModelFragments)
				throw new Exception(element.getElementId() + " is not contained in the application model. " + element);
		}
	}
}
