
package org.eclipse.e4.ui.workbench.addons.swt;

import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;


public class PartLockProcessor {
	@Execute
	void addPartLockAddon(MApplication app, EModelService modelService) {
		List<MAddon> addons = app.getAddons();

		for (MAddon addon : addons) {
			if (addon.getContributionURI().contains(
					"ui.workbench.addons.swt.partlockaddon.PartLockAddon")) //$NON-NLS-1$
				return;
		}

		MAddon partLoackAddon = modelService.createModelElement(MAddon.class);
		partLoackAddon.setElementId("PartLockAddon"); //$NON-NLS-1$
		partLoackAddon
				.setContributionURI("bundleclass://org.eclipse.e4.ui.workbench.addons.swt/org.eclipse.e4.ui.workbench.addons.swt.partlockaddon.PartLockAddon"); //$NON-NLS-1$
		app.getAddons().add(partLoackAddon);

		System.out.println("PartLockProcessor has installed the PartLockAddon"); //$NON-NLS-1$
	}
}
