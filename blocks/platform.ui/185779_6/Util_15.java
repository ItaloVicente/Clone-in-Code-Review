package org.eclipse.e4.ui.workbench.persistence.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.internal.persistence.PartMemento;
import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.persistence.PerspectivePersister;
import org.eclipse.e4.ui.workbench.persistence.common.CommonUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class PerspectivePersisterImpl implements PerspectivePersister {

	@Override
	public String serializePerspectiveAndPartStates(final String perspectiveName) {
		final MWindow mWindow = CommonUtil.getCurrentMainWindow();
		final MPerspective perspective = Util.getMainPerspectiveFromWindow(mWindow, perspectiveName);
		final WorkbenchState workbenchState = Util.createWorkbenchState(mWindow, perspective);
		final String xml = serialize(workbenchState);
		return xml;
	}

	private String serialize(final EObject eObject) {
		final EObject copy = EcoreUtil.copy(eObject);
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(URI.createURI("virtualUri")); //$NON-NLS-1$
		resource.getContents().add(copy);
		try (Writer writer = new StringWriter()) {
			final URIConverter.WriteableOutputStream uws = new URIConverter.WriteableOutputStream(writer, "UTF-8"); //$NON-NLS-1$
			resource.save(uws, null);
			final String xml = writer.toString().trim();
			writer.close();
			return xml;

		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void restoreWorkbenchState(final String serializedPerspective) {
		final WorkbenchState toBeMerged = deserialize(serializedPerspective);
		restoreWorkbenchState(toBeMerged);
	}

	@Override
	public void restoreWorkbenchState(final WorkbenchState workbenchState) {
		final MWindow currentWindow = CommonUtil.getCurrentMainWindow();
		final EPartService ePartService = Util.getEPartService();
		disposeCurrentParts(ePartService.getParts());
		MPerspective perspective = workbenchState.getPerspective();
		ePartService.switchPerspective(perspective.getElementId());

		Shell widget = (Shell) currentWindow.getWidget();
		widget.setRedraw(false);

		try {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			List<IViewPart> viewPartsToHide = new ArrayList<>();
			List<IViewReference> viewReferencesToHide = new ArrayList<>();
			List<MPart> parts = new ArrayList<>();

			for (final PartMemento viewSetting : workbenchState.getViewSettings()) {
				final List<MPart> elements = CommonUtil.getEModelService().findElements(currentWindow,
						viewSetting.getPartId(), MPart.class, null);
				if (elements.size() < 1) {
					System.err.println("Part ID has not been found. View Settings cannot be restored for part: " //$NON-NLS-1$
							+ viewSetting.getPartId());
					continue;
				}

				final MPart part = elements.get(0);
				parts.add(part);

				final Optional<IViewPart> viewPart = CommonUtil.getOptionalViewPart(part);
				if (viewPart.isPresent()) {
					viewPartsToHide.add(viewPart.get());
				} else {
					IViewReference viewRef = (IViewReference) part.getTransientData()
							.get(IWorkbenchPartReference.class.getName());
					if (viewRef != null) {
						IViewReference resolved = activePage.findViewReference(viewRef.getId());
						if (resolved == null) {
							try {
								IViewPart view = activePage.showView(viewRef.getId());
								if (view != null) {
									viewPartsToHide.add(view);
								}
							} catch (PartInitException e) {
								System.err.println("Failed to initialize part"); //$NON-NLS-1$
								e.printStackTrace();
							}
						} else {
							viewReferencesToHide.add(resolved);
						}
					}
				}
			}

			viewReferencesToHide.forEach(activePage::hideView);
			viewPartsToHide.forEach(activePage::hideView);

			for (MPart p : parts) {
				if (p.getIconURI() != null) {
					Image image = JFaceResources.getImageRegistry().get(p.getIconURI());
					if (image != null && image.isDisposed()) {
						JFaceResources.getImageRegistry().remove(p.getIconURI());
					}
				}
			}

			for (final PartMemento viewSetting : workbenchState.getViewSettings()) {
				final List<MPart> elements = CommonUtil.getEModelService().findElements(currentWindow,
						viewSetting.getPartId(), MPart.class, null);
				if (elements.size() < 1) {
					System.err.println("Part ID has not been found. View Settings cannot be restored for part: " //$NON-NLS-1$
							+ viewSetting.getPartId());
					continue;
				}

				final MPart part = elements.get(0);
				parts.add(part);

				part.getPersistedState().put(CommonUtil.MEMENTO_KEY, viewSetting.getMemento());
			}

			replaceExistingPerspective(currentWindow, workbenchState.getPerspective(),
					workbenchState.getPerspective().getElementId());

			if (workbenchState.getEditorArea() != null) {
				replaceEditorAreaContents(currentWindow, workbenchState.getEditorArea());
			}

			if (currentWindow instanceof MTrimmedWindow) {
				replaceSideTrimBars((MTrimmedWindow) currentWindow, workbenchState.getTrimBars());
			}

			MPerspective appliedPerspective = Util.getMainPerspectiveFromWindow(currentWindow,
					workbenchState.getPerspective().getElementId());
			List<MPartStack> stacks = CommonUtil.getEModelService().findElements(appliedPerspective, null, MPartStack.class);
			stacks.stream().filter(s -> s.getTags().contains(IPresentationEngine.MINIMIZED)).forEach(s -> {
				s.getTags().remove(IPresentationEngine.MINIMIZED);
				s.getTags().add(IPresentationEngine.MINIMIZED);
			});
		} finally {
			widget.setRedraw(true);
		}
	}

	private void disposeCurrentParts(Collection<MPart> parts) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		List<IViewPart> viewPartsToHide = new LinkedList<>();
		List<IEditorPart> editorsToHide = new LinkedList<>();
		parts.forEach(part -> {
			Optional<IViewPart> viewPart = CommonUtil.getOptionalViewPart(part);
			if (viewPart.isPresent()) {
				viewPartsToHide.add(viewPart.get());
				return;
			}
			Optional<IEditorPart> editorPart = Util.getOptionalEditorPart(part);
			if (editorPart.isPresent()) {
				editorsToHide.add(editorPart.get());
				return;
			}

			Util.getEPartService().hidePart(part);
		});
		viewPartsToHide.forEach(activePage::hideView);
		editorsToHide.forEach(e -> activePage.closeEditor(e, true));
	}

	private void replaceSideTrimBars(MTrimmedWindow window, List<MTrimBar> trimBars) {
		for (MTrimBar trimBar : trimBars) {
			if (!Util.isSideBar(trimBar)) {
				continue;
			}
			MTrimBar clonedTrimBar = (MTrimBar) CommonUtil.getEModelService().cloneElement(trimBar, null);
			SideValue side = trimBar.getSide();

			Optional<MTrimBar> existingTrimOptional = window.getTrimBars().stream().filter(t -> t.getSide() == side)
					.findFirst();
			if (existingTrimOptional.isPresent()) {
				final MTrimBar existingTrim = existingTrimOptional.get();
				List<MTrimElement> children = new LinkedList<>(existingTrim.getChildren());
				children.forEach(CommonUtil.getEModelService()::deleteModelElement);

				List<MTrimElement> trimElementsToAdd = clonedTrimBar.getChildren().stream()//
						.filter(te -> isTrimPartOfPerspective(te, window))//
						.collect(Collectors.toList());
				existingTrim.getChildren().addAll(trimElementsToAdd);
				existingTrim.getTags().clear();
				existingTrim.getTags().addAll(trimBar.getTags());
				existingTrim.setVisible(trimBar.isVisible());
				existingTrim.setToBeRendered(trimBar.isToBeRendered());
			} else {
				window.getTrimBars().add(clonedTrimBar);
			}
		}
	}

	@SuppressWarnings("restriction")
	private boolean isTrimPartOfPerspective(MTrimElement te, MTrimmedWindow window) {
		EModelService eModelService = CommonUtil.getEModelService();
		MPerspective activePerspective = eModelService.getActivePerspective(window);
		Map<org.eclipse.e4.ui.workbench.addons.minmax.TrimStackIdHelper.TrimStackIdPart, String> parsedIds = org.eclipse.e4.ui.workbench.addons.minmax.TrimStackIdHelper
				.parseTrimStackId(te.getElementId());
		String stackId = parsedIds
				.get(org.eclipse.e4.ui.workbench.addons.minmax.TrimStackIdHelper.TrimStackIdPart.ELEMENT_ID);
		MUIElement foundElement = eModelService.find(stackId, activePerspective);
		return foundElement != null;
	}

	private WorkbenchState deserialize(final String serializedWindow) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(URI.createURI("virtualUri")); //$NON-NLS-1$

		try (Reader reader = new StringReader(serializedWindow)) {
			final URIConverter.ReadableInputStream is = new URIConverter.ReadableInputStream(reader, "UTF-8"); //$NON-NLS-1$
			resource.load(is, null);
			final EObject eObject = resource.getContents().get(0);
			if (!(eObject instanceof WorkbenchState)) {
				throw new IllegalStateException("Corrupted Perspective File"); //$NON-NLS-1$
			}
			return (WorkbenchState) eObject;
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void replaceExistingPerspective(final MWindow currentWindow, final MPerspective toBeMerged,
			final String perspectiveName) {
		final MPerspective currentPerspective = Util.getMainPerspectiveFromWindow(currentWindow, perspectiveName);
		final Control control = (Control) currentPerspective.getWidget();

		MUIElement editorArea = CommonUtil.getEModelService().find(CommonUtil.EDITOR_AREA, currentPerspective);
		if (editorArea != null) {
			editorArea.setToBeRendered(false);
		}

		final MPerspectiveStack mPerspectiveStack = (MPerspectiveStack) deletePerspective(currentPerspective);
		control.dispose();
		final MPerspective clonedPerspective = addPerspective(toBeMerged, mPerspectiveStack, currentWindow);

		final MUIElement activeElement = getActiveElement(clonedPerspective);

		final EPartService ePartService = Util.getEPartService();
		ePartService.switchPerspective(clonedPerspective);

		if (activeElement instanceof MPart) {
			ePartService.activate((MPart) activeElement, true);
		}
	}

	private void replaceEditorAreaContents(final MWindow currentWindow, final MArea toBeMerged) {
		List<MArea> editorAreaList = CommonUtil.getEModelService().findElements(currentWindow, CommonUtil.EDITOR_AREA, MArea.class,
				null, EModelService.OUTSIDE_PERSPECTIVE | EModelService.IN_SHARED_AREA);
		final MSnippetContainer snippetContainer = CommonUtil.createSnippetContainer();
		snippetContainer.getSnippets().add(toBeMerged);
		final MArea cloneSnippet = (MArea) CommonUtil.getEModelService().cloneSnippet(snippetContainer,
				toBeMerged.getElementId(), currentWindow);

		MArea editorArea = editorAreaList.get(0);

		while (editorArea.getChildren().size() > 0) {
			Object widget = editorArea.getChildren().get(0).getWidget();
			EcoreUtil.delete((EObject) editorArea.getChildren().get(0));
			if (widget instanceof Control) {
				((Control) widget).dispose();
			}
		}
		editorArea.getChildren().addAll(cloneSnippet.getChildren());

		activateEditors(editorArea);
	}

	private void activateEditors(MArea editorArea) {
		final EPartService partService = Util.getEPartService();

		final List<MPartStack> partStacks = CommonUtil.getEModelService().findElements(editorArea, null, MPartStack.class);
		final Map<MPartStack, MStackElement> stackSelections = new LinkedHashMap<>();
		for (MPartStack partStack : partStacks) {
			stackSelections.put(partStack, partStack.getSelectedElement());
		}

		final List<MPart> editorParts = CommonUtil.getEModelService().findElements(editorArea, null, MPart.class);
		for (MPart editor : editorParts) {
			partService.activate(editor, false);
		}

		for (MPartStack partStack : stackSelections.keySet()) {
			partStack.setSelectedElement(stackSelections.get(partStack));
		}
	}

	private MUIElement getActiveElement(final MPerspective clonedPerspective) {
		MUIElement activeElement = clonedPerspective;
		while (activeElement instanceof MElementContainer<?>) {
			final MUIElement selectedElement = ((MElementContainer<?>) activeElement).getSelectedElement();
			if (selectedElement == null) {
				break;
			}
			activeElement = selectedElement;
		}

		if (activeElement instanceof MPlaceholder) {
			activeElement = ((MPlaceholder) activeElement).getRef();
		}
		return activeElement;
	}

	private EObject deletePerspective(final MPerspective currentPerspective) {
		final EObject eContainer = ((EObject) currentPerspective).eContainer();
		EcoreUtil.delete((EObject) currentPerspective);
		return eContainer;
	}

	private MPerspective addPerspective(final MPerspective perspective2BeMerged,
			final MPerspectiveStack mPerspectiveStack, final MWindow currentWindow) {
		final MSnippetContainer snippetContainer = CommonUtil.createSnippetContainer();
		snippetContainer.getSnippets().add(perspective2BeMerged);
		final MUIElement cloneSnippet = CommonUtil.getEModelService().cloneSnippet(snippetContainer,
				perspective2BeMerged.getElementId(), currentWindow);
		mPerspectiveStack.getChildren().add((MPerspective) cloneSnippet);
		return (MPerspective) cloneSnippet;
	}

}
