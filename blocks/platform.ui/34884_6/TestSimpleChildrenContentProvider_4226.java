
package org.eclipse.ui.tests.navigator.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;
import org.eclipse.ui.tests.navigator.m12.ResourceWrapperContentProvider;
import org.eclipse.ui.tests.navigator.m12.model.M1Core;
import org.eclipse.ui.tests.navigator.m12.model.M1Project;
import org.eclipse.ui.tests.navigator.m12.model.ResourceWrapper;

public class TestPipelineProvider extends ResourceWrapperContentProvider {
	
	public static final Map ELEMENTS = new HashMap(),
	CHILDREN = new HashMap(),
	ADDS = new HashMap(),
	REMOVES = new HashMap(),
	UPDATES = new HashMap();
	
	private String _id;

	public void getPipelinedChildren(Object aParent, Set theCurrentChildren) {
		_track(CHILDREN, aParent, _id);
	}

	public void getPipelinedElements(Object anInput, Set currentElements) {
		List newElements = new ArrayList();
		for (Iterator it = currentElements.iterator(); it.hasNext();) {
			Object element = it.next();
			IProject project = getProject(element);
			if (project != null) {
				M1Project m1Project = new M1Project(project);
				it.remove();
				newElements.add(m1Project);
			}
		}
		currentElements.addAll(newElements);
		_track(ELEMENTS, anInput, _id);
	}


	public boolean hasPipelinedChildren(Object anInput, boolean currentHasChildren) {
		return currentHasChildren;
	}

	private IProject getProject(Object element) {
		if (element instanceof IProject) {
			return (IProject) element;
		}
		if (element instanceof IAdaptable) {
			return (IProject) ((IAdaptable)element).getAdapter(IProject.class);
		}
		return null;
	}

	private void _track(Map map, Object key, String id) {
		if (key instanceof ResourceWrapper) {
			key = ((ResourceWrapper)key).getResource();
		}
		
		System.out.println("track:  " + mapName(map) + " " + key + " id: " + id);
		
		String queries = (String) map.get(key);
		StringBuffer buf = new StringBuffer(queries==null ? "" : queries);
		buf.append(id);
		map.put(key, buf.toString());		
	}

	public PipelinedShapeModification interceptAdd(PipelinedShapeModification anAddModification) {
		Set children = anAddModification.getChildren();
		for (Iterator it = children.iterator(); it.hasNext(); ) {
			_track(ADDS, it.next(), _id);
		}
		return super.interceptAdd(anAddModification);
	}

	public boolean interceptRefresh(PipelinedViewerUpdate update) {
		Set targets = update.getRefreshTargets();
		for (Iterator it = targets.iterator(); it.hasNext(); ) {
			_track(UPDATES, it.next(), _id);
		}
		return super.interceptRefresh(update);
	}

	public PipelinedShapeModification interceptRemove(PipelinedShapeModification aRemoveModification) {
		Set children = aRemoveModification.getChildren();
		for (Iterator it = children.iterator(); it.hasNext(); ) {
			_track(REMOVES, it.next(), _id);
		}
		return super.interceptRemove(aRemoveModification);
	}

	public boolean interceptUpdate(PipelinedViewerUpdate update) {
		Set targets = update.getRefreshTargets();
		for (Iterator it = targets.iterator(); it.hasNext(); ) {
			_track(UPDATES, it.next(), _id);
		}
		return super.interceptUpdate(update);
	}

	public void init(ICommonContentExtensionSite config) {
		_id = config.getExtension().getId();
		int i = _id.lastIndexOf('.');
		if (i >= 0) {
			_id = _id.substring(i+1);
		}

	}

	public Object[] getChildren(Object parentElement) {
		try {
			_track(CHILDREN, parentElement, _id + "1");
			return ((ResourceWrapper)parentElement).getChildren();
		} catch (CoreException e) {
			e.printStackTrace();
			return NO_CHILDREN;
		}
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return false;
	}

	public Object[] getElements(Object inputElement) {
		_track(ELEMENTS, inputElement, _id + "1");
		return null;
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public void restoreState(IMemento aMemento) {

	}

	public void saveState(IMemento aMemento) {

	}

	protected Object _convertToModelObject(Object object)
	{
		if (object instanceof IResource) {
			return M1Core.getModelObject((IResource)object);
		}
		return null;
	}
	
	public static String mapName(Map map) {
		if (map == ELEMENTS)
			return "ELEMENTS";
		if (map == CHILDREN)
			return "CHILDREN";
		if (map == ADDS)
			return "ADDS";
		if (map == REMOVES)
			return "REMOVES";
		if (map == UPDATES)
			return "UPDATES";
		return "??? unknown";
	}
	
	public static void reset() {
		ELEMENTS.clear();
		CHILDREN.clear();
		ADDS.clear();
		REMOVES.clear();
		UPDATES.clear();
		
	}

}
