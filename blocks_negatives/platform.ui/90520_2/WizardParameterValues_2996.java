/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jan-Hendrik Diederich, Bredex GmbH - bug 201052
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 430616, 441267, 441282, 445609, 441280, 472654
 *     Simon Scholz <scholzsimon@vogella.com> - Bug 473845
 *******************************************************************************/
package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityPart;
import org.eclipse.ui.internal.menus.MenuHelper;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.IStickyViewDescriptor;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;
import org.osgi.framework.Bundle;

public class ViewRegistry implements IViewRegistry {


	/**
	 * This constant is used as key for persisting the original class for a
	 * legacy {@link ViewPart} in the persisted state of a
	 * {@link MPartDescriptor}.
	 */

	/**
	 * This constant is used as key for persisting the original bundle for a
	 * legacy {@link ViewPart} in the persisted state of a
	 * {@link MPartDescriptor}.
	 */

	@Inject
	private MApplication application;

	@Inject
	private EModelService modelService;

	@Inject
	private IExtensionRegistry extensionRegistry;

	@Inject
	private IWorkbench workbench;

	@Inject
	Logger logger;

	private Map<String, IViewDescriptor> descriptors = new HashMap<>();

	private List<IStickyViewDescriptor> stickyDescriptors = new ArrayList<>();

	private HashMap<String, ViewCategory> categories = new HashMap<>();

	private Category miscCategory = new Category();

	@PostConstruct
	void postConstruct() {
		for (IExtension extension : point.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				if (element.getName().equals(IWorkbenchRegistryConstants.TAG_CATEGORY)) {
					ViewCategory category = new ViewCategory(
							element.getAttribute(IWorkbenchRegistryConstants.ATT_ID),
							element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME));
					categories.put(category.getId(), category);
				} else if (element.getName().equals(IWorkbenchRegistryConstants.TAG_STICKYVIEW)) {
					try {
						stickyDescriptors.add(new StickyViewDescriptor(element));
					} catch (CoreException e) {
						logger.error("Unable to create sticky view descriptor.", e.getStatus()); //$NON-NLS-1$
					}
				}
			}
		}
		if (!categories.containsKey(miscCategory.getId())) {
			categories.put(miscCategory.getId(), new ViewCategory(miscCategory.getId(),
					miscCategory.getLabel()));
		}

		for (IExtension extension : point.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				if (element.getName().equals(IWorkbenchRegistryConstants.TAG_VIEW)) {
					createDescriptor(element, false);
				}
				if (element.getName().equals(IWorkbenchRegistryConstants.TAG_E4VIEW)) {
					createDescriptor(element, true);
				}
			}
		}
	}

	private void createDescriptor(IConfigurationElement element, boolean e4View) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		MPartDescriptor descriptor = null;
		List<MPartDescriptor> currentDescriptors = application.getDescriptors();
		for (MPartDescriptor desc : currentDescriptors) {
			if (desc.getElementId().equals(id)) {
				descriptor = desc;
				break;
			}
		}
			descriptor = modelService.createModelElement(MPartDescriptor.class);
			descriptor.setElementId(id);
			application.getDescriptors().add(descriptor);
		}
		descriptor.setLabel(element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME));

		List<String> tags = descriptor.getTags();
		tags.add(VIEW_TAG);

		descriptor.setCloseable(true);
		descriptor.setAllowMultiple(Boolean.parseBoolean(element
				.getAttribute(IWorkbenchRegistryConstants.ATT_ALLOW_MULTIPLE)));

		String viewDescription = RegistryReader.getDescription(element);
		descriptor.setTooltip(viewDescription);

		String clsSpec = element.getAttribute(IWorkbenchConstants.TAG_CLASS);
		String implementationURI = CompatibilityPart.COMPATIBILITY_VIEW_URI;
		if (e4View) {
		} else {
			IExtension declaringExtension = element.getDeclaringExtension();
			String name = declaringExtension.getContributor().getName();

			Bundle bundle = Platform.getBundle(name);
			int colonIndex = clsSpec.indexOf(':');
			String viewClass = colonIndex == -1 ? clsSpec : clsSpec.substring(0, colonIndex);
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_CLASS, viewClass);
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_BUNDLE, bundle.getSymbolicName());

			boolean useDependencyInjection = Boolean
					.parseBoolean(element.getAttribute(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION));
			if (useDependencyInjection) {
				descriptor.getTags().add(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION);
			}
		}
		descriptor.setContributionURI(implementationURI);

		String iconURI = MenuHelper.getIconURI(element, IWorkbenchRegistryConstants.ATT_ICON);
		if (iconURI == null) {
			descriptor.setIconURI(MenuHelper.getImageUrl(workbench.getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_DEF_VIEW)));
		} else {
			descriptor.setIconURI(iconURI);
		}

		String categoryId = element.getAttribute(IWorkbenchRegistryConstants.ATT_CATEGORY);
		ViewCategory category = findCategory(categoryId);
		if (category == null) {
			category = findCategory(miscCategory.getId());
		}
		if (category != null) {
			descriptor.setCategory(category.getLabel());
		}

		ViewDescriptor viewDescriptor = new ViewDescriptor(application, descriptor, element);
		descriptors.put(descriptor.getElementId(), viewDescriptor);
		if (category != null) {
			category.addDescriptor(viewDescriptor);
		}
	}

	@Override
	public IViewDescriptor find(String id) {
		IViewDescriptor candidate = descriptors.get(id);
		if (WorkbenchActivityHelper.restrictUseOf(candidate)) {
			return null;
		}
		return candidate;
	}

	@Override
	public IViewCategory[] getCategories() {
		return categories.values().toArray(new IViewCategory[categories.size()]);
	}

	@Override
	public IViewDescriptor[] getViews() {
		Collection<?> allowedViews = WorkbenchActivityHelper.restrictCollection(
				descriptors.values(), new ArrayList<>());
		return allowedViews.toArray(new IViewDescriptor[allowedViews.size()]);
	}

	@Override
	public IStickyViewDescriptor[] getStickyViews() {
		Collection<?> allowedViews = WorkbenchActivityHelper.restrictCollection(stickyDescriptors,
				new ArrayList<>());
		return allowedViews.toArray(new IStickyViewDescriptor[allowedViews.size()]);
	}

	/**
	 * Returns the {@link ViewCategory} for the given id or <code>null</code> if
	 * one cannot be found or the id is <code>null</code>
	 *
	 * @param id
	 *            the {@link ViewCategory} id
	 * @return the {@link ViewCategory} with the given id or <code>null</code>
	 */
	public ViewCategory findCategory(String id) {
		if (id == null) {
			return categories.get(miscCategory.getId());
		}
		return categories.get(id);
	}

	public Category getMiscCategory() {
		return miscCategory;
	}

}
