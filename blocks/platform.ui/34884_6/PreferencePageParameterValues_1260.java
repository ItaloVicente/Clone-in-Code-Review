package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.e4.compatibility.E4Util;
import org.eclipse.ui.internal.util.PrefUtil;

public class PerspectiveRegistry implements IPerspectiveRegistry, IExtensionChangeHandler {

	@Inject
	private IExtensionRegistry extensionRegistry;

	@Inject
	EModelService modelService;

	@Inject
	MApplication application;

	private Map<String, PerspectiveDescriptor> descriptors = new HashMap<String, PerspectiveDescriptor>();

	@PostConstruct
	void postConstruct(MApplication application) {
		IExtensionPoint point = extensionRegistry.getExtensionPoint("org.eclipse.ui.perspectives"); //$NON-NLS-1$
		for (IConfigurationElement element : point.getConfigurationElements()) {
			String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
			descriptors.put(id, new PerspectiveDescriptor(id, element));
		}
		
		List<MUIElement> snippets = application.getSnippets();
		for (MUIElement snippet : snippets) {
			if (snippet instanceof MPerspective) {
				MPerspective perspective = (MPerspective) snippet;
				String id = perspective.getElementId();
				
				PerspectiveDescriptor existingDescriptor = descriptors.get(id);
				
				if (existingDescriptor == null) {
					String label = perspective.getLabel();
					String originalId = getOriginalId(perspective.getElementId());
					PerspectiveDescriptor originalDescriptor = descriptors.get(originalId);
					PerspectiveDescriptor newDescriptor = new PerspectiveDescriptor(id, label,
							originalDescriptor);
					descriptors.put(id, newDescriptor);
				} else {
					existingDescriptor.setHasCustomDefinition(true);
				}
			}
		}
	}

	public PerspectiveRegistry() {
		IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
		tracker.registerHandler(this, null);

	}

	@Override
	public IPerspectiveDescriptor clonePerspective(String id, String label,
			IPerspectiveDescriptor desc) throws IllegalArgumentException {
		E4Util.unsupported("clonePerspective"); //$NON-NLS-1$
		return null;
	}

	@Override
	public void deletePerspective(IPerspectiveDescriptor toDelete) {
		PerspectiveDescriptor perspective = (PerspectiveDescriptor) toDelete;
		if (perspective.isPredefined())
			return;

		descriptors.remove(perspective.getId());
		removeSnippet(application, perspective.getId());
	}

	private MUIElement removeSnippet(MSnippetContainer snippetContainer, String id) {
		MUIElement snippet = modelService.findSnippet(snippetContainer, id);
		if (snippet != null)
			snippetContainer.getSnippets().remove(snippet);
		return snippet;
	}

	public void deletePerspectives(ArrayList<IPerspectiveDescriptor> perspToDelete) {
		for (int i = 0; i < perspToDelete.size(); i++) {
			deletePerspective(perspToDelete.get(i));
		}
	}


	@Override
	public IPerspectiveDescriptor findPerspectiveWithId(String perspectiveId) {
		return findPerspectiveWithId(perspectiveId, true);
	}

	public IPerspectiveDescriptor findPerspectiveWithId(String perspectiveId,
			boolean considerRestrictRules) {
		IPerspectiveDescriptor candidate = descriptors.get(perspectiveId);
		if (considerRestrictRules && WorkbenchActivityHelper.restrictUseOf(candidate)) {
			return null;
		}
		return candidate;
	}

	@Override
	public IPerspectiveDescriptor findPerspectiveWithLabel(String label) {
		for (IPerspectiveDescriptor descriptor : descriptors.values()) {
			if (descriptor.getLabel().equals(label)) {
				if (WorkbenchActivityHelper.restrictUseOf(descriptor)) {
					return null;
				}
				return descriptor;
			}
		}
		return null;
	}

	@Override
	public String getDefaultPerspective() {
		String defaultId = PrefUtil.getAPIPreferenceStore().getString(
				IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID);
		if (defaultId.length() == 0 || findPerspectiveWithId(defaultId) == null) {
			Workbench instance = Workbench.getInstance();
			return instance == null ? null : instance.getDefaultPerspectiveId();
		}

		return defaultId;
	}

	@Override
	public IPerspectiveDescriptor[] getPerspectives() {
		Collection<?> descs = WorkbenchActivityHelper.restrictCollection(descriptors.values(),
				new ArrayList<Object>());
		return descs.toArray(new IPerspectiveDescriptor[descs.size()]);
	}

	@Override
	public void setDefaultPerspective(String id) {
		IPerspectiveDescriptor desc = findPerspectiveWithId(id);
		if (desc != null) {
			PrefUtil.getAPIPreferenceStore().setValue(
					IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID, id);
		}
	}

	public boolean validateLabel(String label) {
		label = label.trim();
		if (label.length() <= 0) {
			return false;
		}
		return true;
	}


	@Override
	public void revertPerspective(IPerspectiveDescriptor perspToRevert) {
		PerspectiveDescriptor perspective = (PerspectiveDescriptor) perspToRevert;
		if (!perspective.isPredefined())
			return;
		
		perspective.setHasCustomDefinition(false);
		removeSnippet(application, perspective.getId());
	}

	public void dispose() {
		PlatformUI.getWorkbench().getExtensionTracker().unregisterHandler(this);
	}

	@Override
	public void removeExtension(IExtension source, Object[] objects) {
	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension addedExtension) {
	}

	public PerspectiveDescriptor createPerspective(String label,
			PerspectiveDescriptor originalDescriptor) {

		String newID = createNewId(label, originalDescriptor);
		PerspectiveDescriptor newDescriptor = new PerspectiveDescriptor(newID, label,
				originalDescriptor);
		descriptors.put(newDescriptor.getId(), newDescriptor);
		return newDescriptor;
	}

	private String createNewId(String label, PerspectiveDescriptor originalDescriptor) {
		return originalDescriptor.getOriginalId() + '.' + label;
	}

	private String getOriginalId(String id) {
		int index = id.lastIndexOf('.');
		if (index == -1)
			return id;
		return id.substring(0, index);
	}
}
