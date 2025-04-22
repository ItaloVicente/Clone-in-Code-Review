
package org.eclipse.e4.ui.internal.workbench.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.internal.workbench.PartServiceImpl;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public abstract class AbstractCloseFileHandler {

	private boolean closeActivePart;
	private boolean includeSiblings;

	protected AbstractCloseFileHandler(boolean closeActivePart, boolean includeSiblings) {
		this.closeActivePart = closeActivePart;
		this.includeSiblings = includeSiblings;
	}

	@Execute
	public void execute(MPart part, EPartService partService) {
		MElementContainer<MUIElement> partContainer = getParent(part);
		if (partContainer == null)
			return;

		List<MPart> partsToClose = includeSiblings ? gatherSiblingParts(part, closeActivePart)
				: new ArrayList<MPart>(
						(part.isToBeRendered() && isClosable(part)) ? Collections
								.singletonList(part) : Collections.<MPart> emptyList());

		closeParts(partContainer, partsToClose, partService);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}

	private MElementContainer<MUIElement> getParent(MPart part) {
		MElementContainer<MUIElement> parent = part.getParent();
		if (parent == null) {
			MPlaceholder placeholder = part.getCurSharedRef();
			return placeholder == null ? null : placeholder.getParent();
		}
		return parent;
	}

	private boolean isClosable(MPart part) {
		if (part.getCurSharedRef() != null) {
			return !(part.getCurSharedRef().getTags().contains(IPresentationEngine.NO_CLOSE));
		}

		return part.isCloseable();
	}

	private List<MPart> getCloseableSiblingParts(MPart part) {
		MElementContainer<MUIElement> container = getParent(part);
		List<MPart> closeableSiblings = new ArrayList<MPart>();
		if (container == null)
			return closeableSiblings;

		List<MUIElement> children = container.getChildren();
		for (MUIElement child : children) {
			if (!child.isToBeRendered())
				continue;

			MPart otherPart = null;
			if (child instanceof MPart)
				otherPart = (MPart) child;
			else if (child instanceof MPlaceholder) {
				MUIElement otherItem = ((MPlaceholder) child).getRef();
				if (otherItem instanceof MPart)
					otherPart = (MPart) otherItem;
			}
			if (otherPart == null)
				continue;

			if (part.equals(otherPart))
				continue; // skip selected item
			if (otherPart.isToBeRendered() && isClosable(otherPart))
				closeableSiblings.add(otherPart);
		}
		return closeableSiblings;
	}

	protected void closeParts(MElementContainer<MUIElement> partContainer, List<MPart> parts,
			EPartService partService) {
		if (partContainer == null || parts == null || partService == null) {
			throw new NullPointerException();
		}

		boolean saveOK = false;
		if (partService instanceof PartServiceImpl) {
			final PartServiceImpl impl = (PartServiceImpl) partService;
			saveOK = impl.saveParts(parts, true);
		}
		if (saveOK) {
			final MUIElement selectedElement = partContainer.getSelectedElement();
			final MUIElement selectedPartCandidate = selectedElement instanceof MPlaceholder ? ((MPlaceholder) selectedElement)
					.getRef() : selectedElement;
			final MPart selectedPart = selectedPartCandidate instanceof MPart ? (MPart) selectedPartCandidate
					: null;
			for (MPart part : parts) {
				if (part != selectedPart) {
					part.setDirty(false);
					partService.hidePart(part);
				}
			}
			if (parts.contains(selectedPart)) {
				selectedPart.setDirty(false);
				partService.hidePart(selectedPart);
			}
		}
	}

	protected List<MPart> gatherSiblingParts(MPart part, boolean includeThisPart) {
		List<MPart> others = getCloseableSiblingParts(part);

		if (includeThisPart && part.isToBeRendered() && isClosable(part)) {
			others.add(part);
		}
		return others;
	}

}
