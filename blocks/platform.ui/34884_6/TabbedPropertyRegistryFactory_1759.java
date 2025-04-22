package org.eclipse.ui.internal.views.properties.tabbed.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class TabbedPropertyRegistryClassSectionFilter {

	private ITypeMapper typeMapper = null;

	public TabbedPropertyRegistryClassSectionFilter(ITypeMapper typeMapper) {
		super();
		this.typeMapper = typeMapper;
	}

	public boolean appliesToSelection(ISectionDescriptor descriptor,
			ISelection selection) {

		if (selection instanceof IStructuredSelection &&
				selection.isEmpty() == false) {

			if (descriptor.getEnablesFor() != ISectionDescriptor.ENABLES_FOR_ANY &&
					((IStructuredSelection) selection).size() != descriptor
							.getEnablesFor()) {
				return false;
			}

			IFilter filter = descriptor.getFilter();

			if (filter != null) {
				for (Iterator i = ((IStructuredSelection) selection).iterator(); i
						.hasNext();) {
					Object object = i.next();

					if (filter != null && filter.select(object) == false) {
						return false;
					}
				}
				return true;
			}

			Set effectiveTypes = new HashSet();

			for (Iterator i = ((IStructuredSelection) selection).iterator(); i
					.hasNext();) {

				Object object = i.next();

				Class remapType = object.getClass();
				if (typeMapper != null) {
					remapType = typeMapper.mapType(object);
				}

				if (effectiveTypes.add(remapType)) {

					if (appliesToEffectiveType(descriptor, remapType) == false) {
						return false;
					}
				}
			}
		} else {
			if (descriptor.getFilter() != null) {
				return descriptor.getFilter().select(selection);
			}
		}

		return true;
	}

	private boolean appliesToEffectiveType(ISectionDescriptor descriptor,
			Class inputClass) {

		ArrayList classTypes = getClassTypes(inputClass);

		List sectionInputTypes = descriptor.getInputTypes();
		for (Iterator j = sectionInputTypes.iterator(); j.hasNext();) {
			String type = (String) j.next();
			if (classTypes.contains(type)) {
				return true;
			}
		}

		return false;
	}

	protected ArrayList getClassTypes(Class target) {
		ArrayList result = new ArrayList();
		List classes = computeClassOrder(target);
		for (Iterator i = classes.iterator(); i.hasNext();) {
			result.add(((Class) i.next()).getName());
		}
		result.addAll(computeInterfaceOrder(classes));
		return result;
	}

	private List computeClassOrder(Class target) {
		List result = new ArrayList(4);
		Class clazz = target;
		while (clazz != null) {
			result.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return result;
	}

	private List computeInterfaceOrder(List classes) {
		List result = new ArrayList(4);
		Map seen = new HashMap(4);
		for (Iterator iter = classes.iterator(); iter.hasNext();) {
			Class[] interfaces = ((Class) iter.next()).getInterfaces();
			internalComputeInterfaceOrder(interfaces, result, seen);
		}
		return result;
	}

	private void internalComputeInterfaceOrder(Class[] interfaces, List result,
			Map seen) {
		List newInterfaces = new ArrayList(seen.size());
		for (int i = 0; i < interfaces.length; i++) {
			Class interfac = interfaces[i];
			if (seen.get(interfac) == null) {
				result.add(interfac.getName());
				seen.put(interfac, interfac);
				newInterfaces.add(interfac);
			}
		}
		for (Iterator iter = newInterfaces.iterator(); iter.hasNext();) {
			internalComputeInterfaceOrder(
					((Class) iter.next()).getInterfaces(), result, seen);
		}
	}
}
