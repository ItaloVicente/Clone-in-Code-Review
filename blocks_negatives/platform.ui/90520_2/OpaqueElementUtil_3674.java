/*******************************************************************************
 * Copyright (c) 2010, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 434611, 472654
 *     Manumitting Technologies Inc - Bug 380609
 ******************************************************************************/

package org.eclipse.e4.ui.internal.workbench;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.commands.MBindingContext;
import org.eclipse.e4.ui.model.application.commands.MBindingTable;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.commands.MKeyBinding;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MAdvancedFactory;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.model.internal.ModelUtils;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.Selector;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPlaceholderResolver;
import org.eclipse.e4.ui.workbench.modeling.ElementMatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 *
 */
public class ModelServiceImpl implements EModelService {

	private IEclipseContext appContext;

	/** Factory which is able to create {@link MApplicationElement}s in a generic way. */
	private GenericMApplicationElementFactoryImpl mApplicationElementFactory;

	private EventHandler hostedElementHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!changedElement.getTags().contains(HOSTED_ELEMENT)) {
				return;
			}

			if (changedElement.getWidget() != null) {
				return;
			}

			EObject eObj = (EObject) changedElement;
			if (!(eObj.eContainer() instanceof MWindow)) {
				return;
			}

			MWindow hostingWindow = (MWindow) eObj.eContainer();
			hostingWindow.getSharedElements().remove(changedElement);
			changedElement.getTags().remove(HOSTED_ELEMENT);
		}
	};

	/**
	 * This is a singleton service. One instance is used throughout the running application
	 *
	 * @param appContext
	 *            The applicationContext to get the eventBroker from
	 *
	 * @throws NullPointerException
	 *             if the given appContext is <code>null</code>
	 */
	public ModelServiceImpl(IEclipseContext appContext) {
		if (appContext == null)
		 {
		}

		this.appContext = appContext;
		IEventBroker eventBroker = appContext.get(IEventBroker.class);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, hostedElementHandler);

		mApplicationElementFactory = new GenericMApplicationElementFactoryImpl(
				appContext.get(IExtensionRegistry.class));
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <T extends MApplicationElement> T createModelElement(Class<T> elementType) {
		if (elementType == null) {
		}

		T back = (T) mApplicationElementFactory.createEObject(elementType);
		if (back != null) {
			return back;
		}

		throw new IllegalArgumentException(
	}

	private <T> void findElementsRecursive(MApplicationElement searchRoot, Class<T> clazz,
			Selector matcher, List<T> elements, int searchFlags) {
		Assert.isLegal(searchRoot != null);
		if (searchFlags == 0) {
			return;
		}

		boolean classMatch = clazz == null ? true : clazz.isInstance(searchRoot);
		if (classMatch && matcher.select(searchRoot)) {
			if (!elements.contains(searchRoot)) {
				@SuppressWarnings("unchecked")
				T element = (T) searchRoot;
				elements.add(element);
			}
		}
		if (searchRoot instanceof MApplication && (searchFlags == ANYWHERE)) {
			MApplication app = (MApplication) searchRoot;

			List<MApplicationElement> children = new ArrayList<>();
			if (clazz != null) {
				if (clazz.equals(MHandler.class)) {
					children.addAll(app.getHandlers());
				} else if (clazz.equals(MCommand.class)) {
					children.addAll(app.getCommands());
				} else if (clazz.equals(MBindingContext.class)) {
					children.addAll(app.getBindingContexts());
				} else if (clazz.equals(MBindingTable.class) || clazz.equals(MKeyBinding.class)) {
					children.addAll(app.getBindingTables());
				} else if (clazz.equals(MAddon.class)) {
					children.addAll(app.getAddons());
				}
			}

			for (MApplicationElement child : children) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MBindingContext && (searchFlags == ANYWHERE)) {
			MBindingContext bindingContext = (MBindingContext) searchRoot;
			for (MBindingContext child : bindingContext.getChildren()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MBindingTable) {
			MBindingTable bindingTable = (MBindingTable) searchRoot;
			for (MKeyBinding child : bindingTable.getBindings()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MElementContainer<?>) {
			/*
			 * Bug 455281: If given a window with a primary perspective stack,
			 * and we're not told to look outside of the perspectives (i.e.,
			 * searchFlags is missing OUTSIDE_PERSPECTIVE), then just search the
			 * primary perspective stack instead. This ignores special areas
			 * like the compat layer's stack holding the Help, CheatSheets, and
			 * Intro.
			 */
			MElementContainer<?> searchContainer = (MElementContainer<?>) searchRoot;
			MPerspectiveStack primaryStack = null;
			if (searchRoot instanceof MWindow && (searchFlags & OUTSIDE_PERSPECTIVE) == 0
					&& (primaryStack = getPrimaryPerspectiveStack((MWindow) searchRoot)) != null) {
				searchContainer = primaryStack;
			}
			if (searchContainer instanceof MPerspectiveStack) {
				if ((searchFlags & IN_ANY_PERSPECTIVE) != 0) {
					MElementContainer<? extends MUIElement> container = searchContainer;
					List<? extends MUIElement> children = container.getChildren();
					for (MUIElement child : children) {
						findElementsRecursive(child, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_ACTIVE_PERSPECTIVE) != 0) {
					MPerspective active = ((MPerspectiveStack) searchContainer).getSelectedElement();
					if (active != null) {
						findElementsRecursive(active, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_SHARED_AREA) != 0) {
					List<MArea> areas = findElements(searchContainer, null, MArea.class, null);
					for (MArea area : areas) {
						findElementsRecursive(area, clazz, matcher, elements, searchFlags);
					}
				}
			} else {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
				List<MUIElement> children = container.getChildren();
				for (MUIElement child : children) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
				}
			}
		}

		if (searchRoot instanceof MTrimmedWindow && (searchFlags & IN_TRIM) != 0) {
			MTrimmedWindow tw = (MTrimmedWindow) searchRoot;
			List<MTrimBar> bars = tw.getTrimBars();
			for (MTrimBar bar : bars) {
				findElementsRecursive(bar, clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MWindow) {
			MWindow window = (MWindow) searchRoot;
			for (MWindow dw : window.getWindows()) {
				findElementsRecursive(dw, clazz, matcher, elements, searchFlags);
			}

			MMenu menu = window.getMainMenu();
			if (menu != null && (searchFlags & IN_MAIN_MENU) != 0) {
				findElementsRecursive(menu, clazz, matcher, elements, searchFlags);
			}
			if (searchFlags == ANYWHERE && MHandler.class.equals(clazz)) {
				for (MHandler child : window.getHandlers()) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
				}
			}
		}

		if (searchRoot instanceof MPerspective) {
			MPerspective persp = (MPerspective) searchRoot;
			for (MWindow dw : persp.getWindows()) {
				findElementsRecursive(dw, clazz, matcher, elements, searchFlags);
			}
		}
		if (searchRoot instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) searchRoot;

			if (ph.getRef() != null
					&& (!(ph.getRef() instanceof MArea) || (searchFlags & IN_SHARED_AREA) != 0)) {
				findElementsRecursive(ph.getRef(), clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MPart && (searchFlags & IN_PART) != 0) {
			MPart part = (MPart) searchRoot;

			for (MMenu menu : part.getMenus()) {
				findElementsRecursive(menu, clazz, matcher, elements, searchFlags);
			}

			MToolBar toolBar = part.getToolbar();
			if (toolBar != null) {
				findElementsRecursive(toolBar, clazz, matcher, elements, searchFlags);
			}
			if (MHandler.class.equals(clazz)) {
				for (MHandler child : part.getHandlers()) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
				}
			}
		}
	}

	/**
	 * If this window has a primary perspective stack, return it. Otherwise
	 * return null. A primary stack is a single MPerspectiveStack found either
	 * as the window's immediate children or the child under a single
	 * MPartSashContainer.
	 *
	 * @param window
	 *            the window
	 * @return the stack or {@code null}
	 */
	private MPerspectiveStack getPrimaryPerspectiveStack(MWindow window) {
		List<MWindowElement> winKids = window.getChildren();
		if (winKids.isEmpty()) {
			return null;
		}
		if (instanceCount(winKids, MPerspectiveStack.class) == 1) {
			return firstInstance(winKids, MPerspectiveStack.class);
		}
		if (winKids.size() == 1 && winKids.get(0) instanceof MPartSashContainer) {
			MPartSashContainer topLevelPSC = (MPartSashContainer) winKids.get(0);
			if (instanceCount(topLevelPSC.getChildren(), MPerspectiveStack.class) == 1) {
				return firstInstance(topLevelPSC.getChildren(), MPerspectiveStack.class);
			}
		}
		return null;
	}

	/**
	 * Return the first element that is an instance of {@code clazz}
	 *
	 * @param elements
	 * @param clazz
	 * @return the first element that is an instanceof {@code clazz} or null
	 */
	private <T> T firstInstance(Collection<? super T> elements, Class<T> clazz) {
		for (Object o : elements) {
			if (clazz.isInstance(o)) {
				return clazz.cast(o);
			}
		}
		return null;
	}

	/**
	 * Return the number of elements that are an instance of {@code clazz}.
	 *
	 * @param elements
	 *            the elements
	 * @param clazz
	 *            the class
	 * @return the number of elements that are an instance of {@code clazz}
	 */
	private int instanceCount(Collection<?> elements, Class<?> clazz) {
		int count = 0;
		for (Object o : elements) {
			if (clazz.isInstance(o)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public <T> List<T> findElements(MUIElement searchRoot, String id, Class<T> clazz,
			List<String> tagsToMatch) {
		ElementMatcher matcher = new ElementMatcher(id, clazz, tagsToMatch);
		return findElements(searchRoot, clazz, ANYWHERE, matcher);
	}

	@Override
	public <T> List<T> findElements(MUIElement searchRoot, String id, Class<T> clazz,
			List<String> tagsToMatch, int searchFlags) {
		ElementMatcher matcher = new ElementMatcher(id, clazz, tagsToMatch);
		return findElements(searchRoot, clazz, searchFlags, matcher);
	}

	@Override
	public <T> List<T> findElements(MApplicationElement searchRoot, Class<T> clazz,
			int searchFlags, Selector matcher) {
		List<T> elements = new ArrayList<>();
		findElementsRecursive(searchRoot, clazz, matcher, elements, searchFlags);
		return elements;
	}

	private <T> List<T> findPerspectiveElements(MUIElement searchRoot, String id,
			Class<T> clazz,
			List<String> tagsToMatch) {
		List<T> elements = new ArrayList<>();
		ElementMatcher matcher = new ElementMatcher(id, clazz, tagsToMatch);
		findElementsRecursive(searchRoot, clazz, matcher, elements, PRESENTATION);
		return elements;
	}

	@Override
	public MUIElement find(String id, MUIElement searchRoot) {
		if (id == null || id.length() == 0) {
			return null;
		}

		List<MUIElement> elements = findElements(searchRoot, id, MUIElement.class, null);
		if (elements.size() > 0) {
			return elements.get(0);
		}
		return null;
	}

	@Override
	public int countRenderableChildren(MUIElement element) {
		if (!(element instanceof MElementContainer<?>)) {
			return 0;
		}

		@SuppressWarnings("unchecked")
		MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
		int count = 0;
		List<MUIElement> kids = container.getChildren();
		for (MUIElement kid : kids) {
			if (kid.isToBeRendered()) {
				count++;
			}
		}

		if (element instanceof MPerspective) {
			MPerspective perspective = (MPerspective) element;
			for (MWindow window : perspective.getWindows()) {
				if (window.isToBeRendered()) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public IEclipseContext getContainingContext(MUIElement element) {
		return ModelUtils.getContainingContext(element);
	}

	@Override
	public MUIElement cloneElement(MUIElement element, MSnippetContainer snippetContainer) {
		EObject eObj = (EObject) element;
		MUIElement clone = (MUIElement) EcoreUtil.copy(eObj);

		List<MPlaceholder> phList = findElements(clone, null, MPlaceholder.class, null);
		for (MPlaceholder ph : phList) {
			int location = getElementLocation(ph);
			if ((location & IN_SHARED_AREA) != 0) {
				continue;
			}

			ph.setRef(null);
		}

		if (snippetContainer != null) {
			MUIElement snippet = findSnippet(snippetContainer, element.getElementId());
			if (snippet != null) {
				snippetContainer.getSnippets().remove(snippet);
			}
			snippetContainer.getSnippets().add(clone);
		}

		clone.getTransientData().put(CLONED_FROM_KEY, element);

		return clone;
	}

	@Override
	public MUIElement cloneSnippet(MSnippetContainer snippetContainer, String snippetId,
			MWindow refWin) {
		if (snippetContainer == null || snippetId == null || snippetId.length() == 0) {
			return null;
		}

		MApplicationElement elementToClone = null;
		for (MApplicationElement snippet : snippetContainer.getSnippets()) {
			if (snippetId.equals(snippet.getElementId())) {
				elementToClone = snippet;
				break;
			}
		}
		if (elementToClone == null) {
			return null;
		}

		EObject eObj = (EObject) elementToClone;
		MUIElement element = (MUIElement) EcoreUtil.copy(eObj);

		MUIElement appElement = refWin == null ? null : refWin.getParent();
		if (appElement instanceof MApplication) {
			getNullRefPlaceHolders(element, refWin, true);
		}

		return element;
	}

	private List<MPlaceholder> getNullRefPlaceHolders(MUIElement element, MWindow refWin, boolean resolveAlways) {
		EPlaceholderResolver resolver = appContext.get(EPlaceholderResolver.class);
		List<MPlaceholder> phList = findElements(element, null, MPlaceholder.class, null);
		List<MPlaceholder> nullRefList = new ArrayList<>();
		for (MPlaceholder ph : phList) {
			if (resolveAlways) {
				resolver.resolvePlaceholderRef(ph, refWin);
			} else if ((!resolveAlways) && (ph.getRef() == null)) {
				resolver.resolvePlaceholderRef(ph, refWin);
				MUIElement partElement = ph.getRef();
				if (partElement instanceof MPart) {
					MPart part = (MPart) partElement;
					if (part.getIconURI() == null) {
						MPartDescriptor desc = getPartDescriptor(part.getElementId());
						if (desc != null) {
							part.setIconURI(desc.getIconURI());
						}
					}
				}
			}
			if (ph.getRef() == null) {
				nullRefList.add(ph);
			}
		}
		return nullRefList;
	}

	/**
	 * @param element
	 * @param refWin
	 * @return list of null referencing place holders
	 */
	public List<MPlaceholder> getNullRefPlaceHolders(MUIElement element, MWindow refWin) {
		return getNullRefPlaceHolders(element, refWin, false);
	}

	@Override
	public MUIElement findSnippet(MSnippetContainer snippetContainer, String id) {
		if (snippetContainer == null || id == null || id.length() == 0) {
			return null;
		}

		List<MUIElement> snippets = snippetContainer.getSnippets();
		for (MUIElement snippet : snippets) {
			if (id.equals(snippet.getElementId())) {
				return snippet;
			}
		}

		return null;
	}

	@Override
	public void bringToTop(MUIElement element) {
		if (element instanceof MApplication) {
			return;
		}

		MWindow window = getTopLevelWindowFor(element);
		if (window == element) {
			if (!element.isToBeRendered()) {
				element.setToBeRendered(true);
			}

			window.getParent().setSelectedElement(window);
		} else {
			showElementInWindow(window, element);
		}
		UIEvents.publishEvent(UIEvents.UILifeCycle.BRINGTOTOP, element);
	}

	private void showElementInWindow(MWindow window, MUIElement element) {
		MUIElement parent = element.getParent();
		if (parent == null) {
			MPlaceholder ph = findPlaceholderFor(window, element);
			if (ph != null) {
				element = ph;
				parent = element.getParent();
			}
		}

		if (parent == null && element instanceof MWindow) {
			parent = (MUIElement) ((EObject) element).eContainer();
			if (parent != null) {
				if (!element.isToBeRendered()) {
					element.setToBeRendered(true);
				}

				if (window != parent) {
					showElementInWindow(window, parent);
				}
			}
		} else if (parent != null) {
			if (!element.isToBeRendered()) {
				element.setToBeRendered(true);
			}

			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) parent;
			container.setSelectedElement(element);
			if (window != parent) {
				showElementInWindow(window, parent);
			}
		}
	}

	@Override
	public MPlaceholder findPlaceholderFor(MWindow window, MUIElement element) {
		List<MPlaceholder> phList = findPerspectiveElements(window, null, MPlaceholder.class, null);
		List<MPlaceholder> elementRefs = new ArrayList<>();
		for (MPlaceholder ph : phList) {
			if (ph.getRef() == element) {
				elementRefs.add(ph);
			}
		}

		if (elementRefs.size() == 0) {
			return null;
		}

		if (elementRefs.size() == 1) {
			return elementRefs.get(0);
		}

		for (MPlaceholder refPh : elementRefs) {
			int loc = getElementLocation(refPh);
			if ((loc & IN_SHARED_AREA) != 0) {
				return refPh;
			}
		}

		return elementRefs.get(0);
	}

	@Override
	public <T extends MUIElement> void move(T element, MElementContainer<? super T> newParent) {
		move(element, newParent, -1, false);
	}

	@Override
	public <T extends MUIElement> void move(T element, MElementContainer<? super T> newParent,
			boolean leavePlaceholder) {
		move(element, newParent, -1, leavePlaceholder);
	}

	@Override
	public <T extends MUIElement> void move(T element, MElementContainer<? super T> newParent, int index) {
		move(element, newParent, index, false);
	}

	@Override
	public <T extends MUIElement> void move(T element, MElementContainer<? super T> newParent, int index,
			boolean leavePlaceholder) {
		MElementContainer<MUIElement> curParent = element.getParent();
		int curIndex = curParent.getChildren().indexOf(element);

		if (index == -1) {
			newParent.getChildren().add(element);
		} else {
			newParent.getChildren().add(index, element);
		}

		if (leavePlaceholder) {
			MPlaceholder ph = MAdvancedFactory.INSTANCE.createPlaceholder();
			ph.setRef(element);
			curParent.getChildren().add(curIndex, ph);
		}
	}

	private void combine(MPartSashContainerElement toInsert, MPartSashContainerElement relTo,
			MPartSashContainer newSash, boolean newFirst, float ratio) {
		MElementContainer<MUIElement> curParent = relTo.getParent();
		if (curParent == null) {
			MWindow win = getTopLevelWindowFor(relTo);
			relTo = findPlaceholderFor(win, relTo);
			curParent = relTo.getParent();
		}
		Assert.isLegal(relTo != null && curParent != null);
		int index = curParent.getChildren().indexOf(relTo);
		curParent.getChildren().remove(relTo);
		if (newFirst) {
			newSash.getChildren().add(toInsert);
			newSash.getChildren().add(relTo);
		} else {
			newSash.getChildren().add(relTo);
			newSash.getChildren().add(toInsert);
		}

		int adjustedPct = (int) (ratio * 10000);
		toInsert.setContainerData(Integer.toString(adjustedPct));
		relTo.setContainerData(Integer.toString(10000 - adjustedPct));

		curParent.getChildren().add(index, newSash);
	}

	@Override
	public void insert(MPartSashContainerElement toInsert, MPartSashContainerElement relTo,
			int where, float ratio) {
		assert (toInsert != null && relTo != null);
		if (ratio >= 1) {
			warn("EModelService#insert() expects the ratio to be between (0,100)"); //$NON-NLS-1$
		}
		assert(ratio > 0 && ratio < 1);

		boolean insertBefore = where == ABOVE || where == LEFT_OF;
		boolean horizontal = where == LEFT_OF || where == RIGHT_OF;

		MPartSashContainer newSash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
		newSash.setHorizontal(horizontal);

		newSash.setContainerData(relTo.getContainerData());

		combine(toInsert, relTo, newSash, insertBefore, ratio);
	}

	@Override
	public void detach(MPartSashContainerElement element, int x, int y, int width, int height) {
		if (element.getCurSharedRef() != null) {
			element = element.getCurSharedRef();
		}

		MWindow window = getTopLevelWindowFor(element);
		MPerspective thePersp = getPerspectiveFor(element);

		MTrimmedWindow newWindow = MBasicFactory.INSTANCE.createTrimmedWindow();

		newWindow.setX(x);
		newWindow.setY(y);
		newWindow.setWidth(width);
		newWindow.setHeight(height);

		element.getParent().getChildren().remove(element);
		MWindowElement uiRoot = wrapElementForWindow(element);
		newWindow.getChildren().add(uiRoot);

		if (thePersp != null) {
			thePersp.getWindows().add(newWindow);
		} else if (window != null) {
			window.getWindows().add(newWindow);
		}
	}

	/**
	 * Wraps an element in a PartStack if it's a MPart or an MPlaceholder that references an MPart
	 *
	 * @param element
	 *            The element to be wrapped
	 * @return The wrapper for the given element
	 */
	private MWindowElement wrapElementForWindow(MPartSashContainerElement element) {
		if (element instanceof MPlaceholder) {
			MUIElement ref = ((MPlaceholder) element).getRef();
			if (ref instanceof MPart) {
				MPartStack newPS = MBasicFactory.INSTANCE.createPartStack();
				newPS.getChildren().add((MPlaceholder) element);
				return newPS;
			}
		} else if (element instanceof MPart) {
			MPartStack newPS = MBasicFactory.INSTANCE.createPartStack();
			newPS.getChildren().add((MPart) element);
			return newPS;
		} else if (element instanceof MWindowElement) {
			return (MWindowElement) element;
		}
		return null;
	}

	@Override
	public MTrimBar getTrim(MTrimmedWindow window, SideValue sv) {
		List<MTrimBar> bars = window.getTrimBars();
		for (MTrimBar bar : bars) {
			if (bar.getSide() == sv) {
				return bar;
			}
		}

		MTrimBar newBar = BasicFactoryImpl.eINSTANCE.createTrimBar();

		if (sv == SideValue.TOP) {
		} else if (sv == SideValue.BOTTOM) {
		} else if (sv == SideValue.LEFT) {
		} else if (sv == SideValue.RIGHT) {
		}

		newBar.setSide(sv);
		window.getTrimBars().add(newBar);
		return newBar;
	}

	@Override
	public MWindow getTopLevelWindowFor(MUIElement element) {
		EObject eObj = (EObject) element;
		while (eObj != null && !(eObj.eContainer() instanceof MApplication)) {
			eObj = eObj.eContainer();
		}

		if (eObj instanceof MWindow) {
			return (MWindow) eObj;
		}

	}

	@Override
	public MPerspective getPerspectiveFor(MUIElement element) {

		while (true) {
			MPlaceholder placeholder = element.getCurSharedRef();
			if (placeholder != null) {
				element = placeholder;
			}
			EObject container = ((EObject) element).eContainer();
			if (container == null || container instanceof MApplication) {
				return null;
			} else if (container instanceof MPerspectiveStack) {
				return (MPerspective) element;
			}

			element = (MUIElement) container;
		}
	}

	@Override
	public void resetPerspectiveModel(MPerspective persp, MWindow window) {
		resetPerspectiveModel(persp, window, true);
	}

	private void resetPerspectiveModel(MPerspective persp, MWindow window,
			boolean removeSharedPlaceholders) {
		if (persp == null) {
			return;
		}

		if (removeSharedPlaceholders) {
			EPartService ps = window.getContext().get(EPartService.class);
			List<MArea> areas = findElements(window, null, MArea.class, null);
			if (areas.size() == 1) {
				MArea area = areas.get(0);

				List<MPlaceholder> phList = findElements(area, null, MPlaceholder.class, null);
				for (MPlaceholder ph : phList) {
					ps.hidePart((MPart) ph.getRef());
					ph.getParent().getChildren().remove(ph);
				}

				List<MPartStack> stacks = findElements(area, null, MPartStack.class, null);
				for (MPartStack stack : stacks) {
					stack.setElementId(generatedId);
				}

				MUIElement areaPresentation = area;
				if (area.getCurSharedRef() != null) {
					areaPresentation = area.getCurSharedRef();
				}

				areaPresentation.getTags().remove(IPresentationEngine.MAXIMIZED);
				areaPresentation.getTags().remove(IPresentationEngine.MINIMIZED);
				areaPresentation.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
			}
		}

		List<MTrimBar> bars = findElements(window, null, MTrimBar.class, null);
		List<MToolControl> toRemove = new ArrayList<>();
		for (MTrimBar bar : bars) {
			for (MUIElement barKid : bar.getChildren()) {
				if (!(barKid instanceof MToolControl)) {
					continue;
				}
				String id = barKid.getElementId();
				if (id != null && id.contains(persp.getElementId())) {
					toRemove.add((MToolControl) barKid);
				}
			}
		}

		for (MToolControl toolControl : toRemove) {
			toolControl.setToBeRendered(false);
			toolControl.getParent().getChildren().remove(toolControl);
		}
	}

	@Override
	public void removePerspectiveModel(MPerspective persp, MWindow window) {
		MUIElement psElement = persp.getParent();
		MPerspectiveStack ps = (MPerspectiveStack) psElement;
		boolean foundNewSelection = false;
		if (ps.getSelectedElement() == persp) {
			for (MPerspective p : ps.getChildren()) {
				if (p != persp && p.isToBeRendered()) {
					ps.setSelectedElement(p);
					foundNewSelection = true;
					break;
				}
			}

			if (!foundNewSelection) {
				ps.setSelectedElement(null);
			}
		}

		resetPerspectiveModel(persp, window, false);

		persp.setToBeRendered(false);
		ps.getChildren().remove(persp);
	}

	@Override
	public MPerspective getActivePerspective(MWindow window) {
		List<MPerspectiveStack> pStacks = findElements(window, null, MPerspectiveStack.class, null);
		if (pStacks.size() == 1) {
			MPerspective perspective = pStacks.get(0).getSelectedElement();
			return perspective;
		}

		return null;
	}

	@Override
	public int toBeRenderedCount(MElementContainer<?> container) {
		int count = 0;
		for (MUIElement child : container.getChildren()) {
			if (child.isToBeRendered()) {
				count++;
			}
		}
		return count;
	}

	@Override
	public MUIElement getContainer(MUIElement element) {
		if (element == null) {
			return null;
		}

		return (MUIElement) ((EObject) element).eContainer();
	}

	@Override
	public int getElementLocation(MUIElement element) {
		if (element == null) {
			return NOT_IN_UI;
		}

		if (element.getCurSharedRef() != null) {
			element = element.getCurSharedRef();
		}

		int location = NOT_IN_UI;
		MUIElement curElement = element;
		while (curElement != null) {
			Object container = ((EObject) curElement).eContainer();
			if (!(container instanceof MUIElement))
				return NOT_IN_UI;

			if (container instanceof MApplication) {
				if (location != NOT_IN_UI)
					return location;
				return OUTSIDE_PERSPECTIVE;
			} else if (container instanceof MPerspective) {
				MPerspective perspective = (MPerspective) container;
				MUIElement perspParent = perspective.getParent();
				if (perspParent == null) {
					location = NOT_IN_UI;
				} else if (perspective.getParent().getSelectedElement() == perspective) {
					location |= IN_ACTIVE_PERSPECTIVE;
				} else {
					location |= IN_ANY_PERSPECTIVE;
				}
			} else if (container instanceof MTrimBar) {
				location = IN_TRIM;
			} else if (container instanceof MArea) {
				location = IN_SHARED_AREA;
			}

			curElement = (MUIElement) container;
		}

		return NOT_IN_UI;
	}

	@Override
	public MPartDescriptor getPartDescriptor(String id) {
		MApplication application = appContext.get(MApplication.class);

		int colonIndex = id == null ? -1 : id.indexOf(':');
		String descId = colonIndex == -1 ? id : id.substring(0, colonIndex);

		for (MPartDescriptor descriptor : application.getDescriptors()) {
			if (descriptor.getElementId().equals(descId)) {
				return descriptor;
			}
		}
		return null;
	}

	@Override
	public MPart createPart(MPartDescriptor descriptor) {
		if (descriptor == null) {
			return null;
		}
		MPart part = createModelElement(MPart.class);
		part.setElementId(descriptor.getElementId());
		part.getMenus().addAll(EcoreUtil.copyAll(descriptor.getMenus()));
		if (descriptor.getToolbar() != null) {
			part.setToolbar((MToolBar) EcoreUtil.copy((EObject) descriptor.getToolbar()));
		}
		part.setContributorURI(descriptor.getContributorURI());
		part.setCloseable(descriptor.isCloseable());
		part.setContributionURI(descriptor.getContributionURI());
		part.setLabel(descriptor.getLabel());
		part.setIconURI(descriptor.getIconURI());
		part.setTooltip(descriptor.getTooltip());
		part.getHandlers().addAll(EcoreUtil.copyAll(descriptor.getHandlers()));
		part.getTags().addAll(descriptor.getTags());
		part.getVariables().addAll(descriptor.getVariables());
		part.getProperties().putAll(descriptor.getProperties());
		part.getPersistedState().putAll(descriptor.getPersistedState());
		part.getBindingContexts().addAll(descriptor.getBindingContexts());
		if (descriptor.getTrimBars() != null) {
			part.getTrimBars().addAll(EcoreUtil.copyAll(descriptor.getTrimBars()));
		}
		return part;
	}

	@Override
	public void hideLocalPlaceholders(MWindow window, MPerspective perspective) {
		List<MPlaceholder> globals = findElements(window, null, MPlaceholder.class, null,
				OUTSIDE_PERSPECTIVE | IN_SHARED_AREA);

		List<MPerspective> persps = new ArrayList<>();
		if (perspective != null) {
			persps.add(perspective);
		} else {
			persps = findElements(window, null, MPerspective.class, null);
		}

		for (MPerspective persp : persps) {
			List<MPlaceholder> locals = findElements(persp, null, MPlaceholder.class, null,
					IN_ANY_PERSPECTIVE);
			for (MPlaceholder local : locals) {
				for (MPlaceholder global : globals) {
					if (global.getRef() == local.getRef()) {
						local.setToBeRendered(false);
						MElementContainer<MUIElement> localParent = local.getParent();
						setStackVisibility(localParent);
					}
				}
			}
		}
	}

	/**
	 * @param parent
	 */
	private void setStackVisibility(MElementContainer<MUIElement> parent) {
		for (MUIElement child : parent.getChildren()) {
			if (child.isToBeRendered() && child.isVisible()) {
				parent.setToBeRendered(true);
				return;
			}
		}
		parent.setToBeRendered(false);
		setStackVisibility(parent.getParent());
	}

	@Override
	public boolean isLastEditorStack(MUIElement stack) {
		if (!(stack instanceof MPartStack)) {
			return false;
		}

		MUIElement parent = stack.getParent();
		while (parent != null && !(parent instanceof MArea)) {
			parent = parent.getParent();
		}
		if (parent == null) {
			return false;
		}

		MArea area = (MArea) parent;
		List<MPartStack> stacks = findElements(area, null, MPartStack.class, null);
		int count = 0;
		for (MPartStack aStack : stacks) {
			if (aStack.isToBeRendered()) {
				count++;
			}
		}
		return count < 2 && stack.isToBeRendered();
	}

	@Override
	public void hostElement(MUIElement element, MWindow hostWindow, Object uiContainer,
			IEclipseContext hostContext) {
		hostWindow.getSharedElements().add(element);
		element.getTags().add(HOSTED_ELEMENT);

		IPresentationEngine renderer = hostWindow.getContext().get(IPresentationEngine.class);
		renderer.createGui(element, uiContainer, hostContext);
	}

	@Override
	public boolean isHostedElement(MUIElement element, MWindow hostWindow) {
		MUIElement curElement = element;
		while (curElement != null && !curElement.getTags().contains(HOSTED_ELEMENT)) {
			if (curElement.getCurSharedRef() != null) {
				curElement = curElement.getCurSharedRef();
			} else {
				curElement = curElement.getParent();
			}
		}

		if (curElement == null) {
			return false;
		}

		return hostWindow.getSharedElements().contains(curElement);
	}

	private void warn(String message) {
		Logger logger = appContext.get(Logger.class);
		if (logger != null) {
			logger.warn(message);
		}
	}
}
