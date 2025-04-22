
package org.eclipse.e4.ui.workbench.addons.minmax;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class MinMaxAddonUtil {

	private static final String ID_EDITOR_AREA = "org.eclipse.ui.editorss"; //$NON-NLS-1$
	private static String MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG = IPresentationEngine.MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG;

	public static boolean isMinMaxChildrenAreaWithMultipleVisibleChildren(MUIElement element) {
		if (!element.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG))
			return false;
		if (!(element instanceof MArea))
			return false;
		MArea area = (MArea) element;
		if (area.getChildren().isEmpty())
			return false;
		if (!hasMoreThenOneVisibleRenderableChild(area.getChildren().get(0)))
			return false;
		return true;
	}

	private static boolean hasMoreThenOneVisibleRenderableChild(MPartSashContainerElement elementToCheck) {
		if (elementToCheck instanceof MPartSashContainer) {
			int partsToRender = 0;
			for (MPartSashContainerElement part : ((MPartSashContainer) elementToCheck).getChildren()) {

				boolean hasMinimizeableChild = hasMoreThenOneVisibleRenderableChild(part);

				if (hasMinimizeableChild) {
					return true;
				}
				if (isVisible(part))
					partsToRender++;
			}
			if (partsToRender > 1)
				return true;
		}
		return false;
	}

	private static boolean isVisible(MUIElement part) {
		boolean visible = part.isToBeRendered() && part.isVisible();
		if (part instanceof MElementContainer && visible) {
			visible = false;
			for (Object element : ((MElementContainer<?>) part).getChildren()) {
				MUIElement innerElement = (MUIElement) element;
				visible |= isVisible(innerElement);
			}
		}
		return visible;
	}

	public static void ignoreChildrenOfMinMaxChildrenArea(EModelService modelService, MUIElement element,
			List<MUIElement> curMax) {
		if (element instanceof MPlaceholder
				&& ((MPlaceholder) element).getRef().getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG)) {
			Set<MUIElement> toRemove = new LinkedHashSet<MUIElement>();
			for (MUIElement maxElement : curMax) {
				if (modelService.find(maxElement.getElementId(), element) != null) {
					toRemove.add(maxElement);
				}
			}
			curMax.removeAll(toRemove);
		}
	}

	public static void addChildrenOfMinMaxChildrenAreaToRestoreList(EModelService modelService, MUIElement element,
			MWindow win, MPerspective persp, List<MUIElement> elementsToRestore) {
		List<MPlaceholder> areas = modelService.findElements(persp == null ? win : persp, ID_EDITOR_AREA,
				MPlaceholder.class, null, EModelService.PRESENTATION);

		for (MPlaceholder placeholder : areas) {
			if (placeholder == element)
				continue;
			if (win != getWindowFor(placeholder))
				continue;
			if (!placeholder.getRef().getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG))
				continue;
			List<MPartStack> partStacks = modelService.findElements(placeholder, null, MPartStack.class,
					null);
			if (partStacks.contains(element))
				continue;
			for (MPartStack partStack : partStacks) {
				elementsToRestore.remove(partStack);
			}
		}
	}

	public static void restoreStacksOfMinMaxChildrenArea(final MinMaxAddon minMaxAddon, MUIElement element,
			List<String> maximizeTag) {
		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG)) {
				final List<MPartStack> maximizedAreaChildren = minMaxAddon.modelService.findElements(area, null,
						MPartStack.class,
						maximizeTag);
				minMaxAddon.executeWithIgnoredTagChanges(new Runnable() {

					@Override
					public void run() {
						for (MPartStack partStack : maximizedAreaChildren) {
							partStack.getTags().remove(IPresentationEngine.MAXIMIZED);
							minMaxAddon.adjustCTFButtons(partStack);
						}
					}
				});
			}
		}
	}

	public static void maximizeMinMaxChildrenArea(final MinMaxAddon minMaxAddon, final MUIElement element) {
		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG)) {
				final MPlaceholder placeholder = area.getCurSharedRef();
				minMaxAddon.executeWithIgnoredTagChanges(new Runnable() {

					@Override
					public void run() {
						placeholder.getTags().add(IPresentationEngine.MAXIMIZED);
					}
				});
				minMaxAddon.adjustCTFButtons(placeholder);
			}
		}
	}

	public static void handleMinimizeOfMinMaxChildrenArea(EModelService modelService, MUIElement element, MWindow win,
			MPerspective persp, List<MUIElement> elementsToMinimize) {
		List<MPlaceholder> areas = modelService.findElements(persp == null ? win : persp, ID_EDITOR_AREA,
				MPlaceholder.class, null, EModelService.ANYWHERE);
		boolean foundRelevantArea = false;
		for (MPlaceholder placeholder : areas) {
			if (placeholder == element)
				continue;
			if (win != getWindowFor(placeholder))
				continue;
			if (modelService.find(element.getElementId(), placeholder) == null)
				continue;
			if (placeholder.getRef().getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG))
				foundRelevantArea = true;
			List<MPartStack> partStacks = modelService.findElements(placeholder, null, MPartStack.class,
					null);
			for (MPartStack partStack : partStacks) {
				if (partStack == element)
					continue;
				elementsToMinimize.add(partStack);
			}
		}
		if (foundRelevantArea) {
			List<MUIElement> elementsToRemove = new ArrayList<MUIElement>();
			for (MUIElement element2 : elementsToMinimize) {
				List<Object> findElements = modelService.findElements(element2, element.getElementId(),
						null, null);
				if (findElements != null && findElements.size() != 0)
					elementsToRemove.add(element2);
			}
			elementsToMinimize.removeAll(elementsToRemove);
		}
	}

	public static void unzoomStackOfMinMaxChildrenArea(final MinMaxAddon minMaxAddon, final MUIElement element) {
		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG)) {
				final MPlaceholder placeholder = area.getCurSharedRef();
				minMaxAddon.executeWithIgnoredTagChanges(new Runnable() {

					@Override
					public void run() {
						placeholder.getTags().remove(IPresentationEngine.MAXIMIZED);
					}
				});
				minMaxAddon.adjustCTFButtons(placeholder);
			}
		}
	}

	public static boolean isPartOfMinMaxChildrenArea(MUIElement element) {
		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG))
				return true;
		}
		return false;
	}

	public static MArea getAreaFor(MPartStack stack) {
		MUIElement parent = stack.getParent();
		while (parent != null) {
			if (parent instanceof MArea)
				return (MArea) parent;
			parent = parent.getParent();
		}
		return null;
	}

	public static MWindow getWindowFor(MUIElement element) {
		MUIElement parent = element.getParent();

		while (parent != null && !(parent instanceof MWindow)) {
			if (parent.getTags().contains(MIN_MAXIMIZEABLE_CHILDREN_AREA_TAG) && parent instanceof MArea)
				parent = ((MArea) parent).getCurSharedRef();
			else
				parent = parent.getParent();
		}

		return (MWindow) parent;
	}
}
