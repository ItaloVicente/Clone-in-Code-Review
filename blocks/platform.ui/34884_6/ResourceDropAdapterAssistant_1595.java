
package org.eclipse.ui.navigator.resources;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.internal.navigator.Policy;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;
import org.eclipse.ui.navigator.INavigatorDnDService;
import org.eclipse.ui.part.ResourceTransfer;

public class ResourceDragAdapterAssistant extends
		CommonDragAdapterAssistant {

	private static final Transfer[] SUPPORTED_TRANSFERS = new Transfer[] {
			ResourceTransfer.getInstance(),
			FileTransfer.getInstance() };

	private static final Class<IResource> IRESOURCE_TYPE = IResource.class;

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return SUPPORTED_TRANSFERS;
	}

	@Override
	public boolean setDragData(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {

		IResource[] resources = getSelectedResources(aSelection);
		if (resources.length > 0) {
			if (ResourceTransfer.getInstance().isSupportedType(anEvent.dataType)) {
				anEvent.data = resources;
				if (Policy.DEBUG_DND) {
					System.out
							.println("ResourceDragAdapterAssistant.dragSetData set ResourceTransfer"); //$NON-NLS-1$
				}
				return true;
			} 
				
			if (FileTransfer.getInstance().isSupportedType(anEvent.dataType)) {
				final int length = resources.length;
				int actualLength = 0;
				String[] fileNames = new String[length];
				for (int i = 0; i < length; i++) {
					IPath location = resources[i].getLocation();
					if (location != null) {
						fileNames[actualLength++] = location.toOSString();
					}
				}
				if (actualLength > 0) { 
					if (actualLength < length) {
						String[] tempFileNames = fileNames;
						fileNames = new String[actualLength];
						for (int i = 0; i < actualLength; i++)
							fileNames[i] = tempFileNames[i];
					}
					anEvent.data = fileNames;
		
					if (Policy.DEBUG_DND)
						System.out
								.println("ResourceDragAdapterAssistant.dragSetData set FileTransfer"); //$NON-NLS-1$
					return true;
				}
			}
		}
		return false;

	}

	private IResource[] getSelectedResources(IStructuredSelection aSelection) {
		Set<IResource> resources = new LinkedHashSet<IResource>();
		IResource resource = null;
		for (Iterator<?> iter = aSelection.iterator(); iter.hasNext();) {
			Object selected = iter.next();
			resource = adaptToResource(selected);
			if (resource != null) {
				resources.add(resource);
		}
		}
		return resources.toArray(new IResource[resources.size()]);
	}

	private IResource adaptToResource(Object selected) {
		IResource resource;
		if (selected instanceof IResource) {
			resource = (IResource) selected;
		} else if (selected instanceof IAdaptable) {
			resource = (IResource) ((IAdaptable) selected)
					.getAdapter(IRESOURCE_TYPE);
		} else {
			resource = (IResource) Platform.getAdapterManager().getAdapter(
					selected, IRESOURCE_TYPE);
		}
		return resource;
	}

}
