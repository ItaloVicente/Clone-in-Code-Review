
package org.eclipse.e4.ui.workbench.addons.swt;

import java.util.List;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.workbench.Selector;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class ProcessorUtil {

	public static void addAddon(MApplication app, EModelService modelService, final String URI,
			final String id) {

		List<MAddon> addons = modelService.findElements(app, MAddon.class, EModelService.ANYWHERE,
				new Selector() {
					@Override
					public boolean select(MApplicationElement element) {

						if (!(element instanceof MContribution)) {
							return false;
						}

						MContribution contrib = (MContribution) element;

						return contrib.getContributionURI().equals(URI);
					}
				});

		if (addons.size() > 0) {
			return;
		}

		MAddon dndAddon = modelService.createModelElement(MAddon.class);
		dndAddon.setElementId(id);
		dndAddon.setContributionURI(URI);
		app.getAddons().add(dndAddon);
	}
}
