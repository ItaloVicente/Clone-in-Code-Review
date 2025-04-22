
package org.eclipse.e4.ui.internal.workbench;

import java.util.List;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.AdvancedFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPlaceholderResolver;

/**
 * Default implementation of a resolver that expects the id of the placeholder to match the id of a
 * PartDescriptor. It will create an MPart from the descriptor and add it to the appropriate
 * window'w 'sharedElementList' if necessary.
 */
public class PlaceholderResolver implements EPlaceholderResolver {
	@Override
	public void resolvePlaceholderRef(MPlaceholder ph, MWindow refWin) {
		if (ph.getRef() != null)
			return;

		Assert.isLegal(refWin != null);

		MUIElement refParent = refWin.getParent();
		Assert.isLegal(refParent instanceof MApplication);

		List<MUIElement> sharedElements = refWin.getSharedElements();
		for (MUIElement se : sharedElements) {
			if (ph.getElementId().equals(se.getElementId())) {
				ph.setRef(se);
				return;
			}
		}

			MArea sharedArea = AdvancedFactoryImpl.eINSTANCE.createArea();

			MPartStack editorStack = BasicFactoryImpl.eINSTANCE.createPartStack();
			sharedArea.getChildren().add(editorStack);
			sharedArea.setElementId(ph.getElementId());

			refWin.getSharedElements().add(sharedArea);
			ph.setRef(sharedArea);
			return;
		}

		EPartService ps = refWin.getContext().get(EPartService.class);
		MPart newReferencedPart = ps.createPart(ph.getElementId());
		if (newReferencedPart != null) {
			refWin.getSharedElements().add(newReferencedPart);
			ph.setRef(newReferencedPart);
			return;
		}

	}

}
