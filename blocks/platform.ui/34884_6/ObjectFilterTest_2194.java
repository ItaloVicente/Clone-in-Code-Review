package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.util.Util;

public abstract class ObjectContributorManager implements IExtensionChangeHandler {
	
	private class ContributorRecord {
		public ContributorRecord(IObjectContributor contributor, String targetType) {
			this.contributor = contributor;
			this.objectClassName = targetType;
		}
		
		String objectClassName;
		IObjectContributor contributor;
	}

    protected Map contributors;

    protected Map objectLookup;

    protected Map resourceAdapterLookup;
    
    protected Map adaptableLookup;
    
    protected Set contributorRecordSet;

    public ObjectContributorManager() {
    	contributors = new Hashtable(5);
        contributorRecordSet = new HashSet(5);
        objectLookup = null;
        resourceAdapterLookup = null;
        adaptableLookup = null;
        String extensionPointId = getExtensionPointFilter();
        if (extensionPointId != null) {
        	IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
    				PlatformUI.PLUGIN_ID, extensionPointId);
			IExtensionTracker tracker = PlatformUI.getWorkbench()
					.getExtensionTracker();
			tracker.registerHandler(this, ExtensionTracker
					.createExtensionPointFilter(extensionPoint));
		}
    }

	protected String getExtensionPointFilter() {
		return null;
	}

    private void addContributorsFor(List types, List result) {
        for (Iterator classes = types.iterator(); classes.hasNext();) {
            Class clazz = (Class) classes.next();
            List contributorList = (List) contributors.get(clazz.getName());
            if (contributorList != null) {
				result.addAll(contributorList);
			}
        }
    }

    protected final List computeClassOrder(Class extensibleClass) {
        ArrayList result = new ArrayList(4);
        Class clazz = extensibleClass;
        while (clazz != null) {
            result.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    protected final List computeInterfaceOrder(List classList) {
        ArrayList result = new ArrayList(4);
        Map seen = new HashMap(4);
        for (Iterator list = classList.iterator(); list.hasNext();) {
            Class[] interfaces = ((Class) list.next()).getInterfaces();
            internalComputeInterfaceOrder(interfaces, result, seen);
        }
        return result;
    }

    public void flushLookup() {
        objectLookup = null;
        resourceAdapterLookup = null;
        adaptableLookup = null;
    }

    private void cacheResourceAdapterLookup(Class adapterClass, List results) {
        if (resourceAdapterLookup == null) {
			resourceAdapterLookup = new HashMap();
		}
        resourceAdapterLookup.put(adapterClass, results);
    }
    
    private void cacheAdaptableLookup(String adapterClass, List results) {
        if (adaptableLookup == null) {
			adaptableLookup = new HashMap();
		}
        adaptableLookup.put(adapterClass, results);
    }

    private void cacheObjectLookup(Class objectClass, List results) {
        if (objectLookup == null) {
			objectLookup = new HashMap();
		}
        objectLookup.put(objectClass, results);
    }

    public Collection getContributors() {
        return Collections.unmodifiableCollection(contributors.values());
    }

    protected List addContributorsFor(Class objectClass) {

        List classList = computeClassOrder(objectClass);
        List result = new ArrayList();
        addContributorsFor(classList, result);
        classList = computeInterfaceOrder(classList); // interfaces
        addContributorsFor(classList, result);
        return result;
    }

    public boolean hasContributorsFor(Object object) {

        List contributors = getContributors(object);
        return contributors.size() > 0;
    }

    private void internalComputeInterfaceOrder(Class[] interfaces, List result,
            Map seen) {
        List newInterfaces = new ArrayList(seen.size());
        for (int i = 0; i < interfaces.length; i++) {
            Class interfac = interfaces[i];
            if (seen.get(interfac) == null) {
                result.add(interfac);
                seen.put(interfac, interfac);
                newInterfaces.add(interfac);
            }
        }
        for (Iterator newList = newInterfaces.iterator(); newList.hasNext();) {
			internalComputeInterfaceOrder(((Class) newList.next())
                    .getInterfaces(), result, seen);
		}
    }

    public boolean isApplicableTo(IStructuredSelection selection,
            IObjectContributor contributor) {
        Iterator elements = selection.iterator();
        while (elements.hasNext()) {
            if (contributor.isApplicableTo(elements.next()) == false) {
				return false;
			}
        }
        return true;
    }


    public boolean isApplicableTo(List list, IObjectContributor contributor) {
        Iterator elements = list.iterator();
        while (elements.hasNext()) {
            if (contributor.isApplicableTo(elements.next()) == false) {
				return false;
			}
        }
        return true;
    }

    public void registerContributor(IObjectContributor contributor,
            String targetType) {
        List contributorList = (List) contributors.get(targetType);
        if (contributorList == null) {
            contributorList = new ArrayList(5);
            contributors.put(targetType, contributorList);
        }
        contributorList.add(contributor);
        flushLookup();

        IConfigurationElement element = (IConfigurationElement) Util.getAdapter(contributor,
        	IConfigurationElement.class);
        
        if (element != null) {
			ContributorRecord contributorRecord = new ContributorRecord(
					contributor, targetType);
			contributorRecordSet.add(contributorRecord);
			PlatformUI.getWorkbench().getExtensionTracker().registerObject(
					element.getDeclaringExtension(), contributorRecord,
					IExtensionTracker.REF_WEAK);
        }
    }

    public void unregisterAllContributors() {
        contributors = new Hashtable(5);
        flushLookup();
    }

    public void unregisterContributor(IObjectContributor contributor,
            String targetType) {    	
        List contributorList = (List) contributors.get(targetType);
        if (contributorList == null) {
			return;
		}
        contributorList.remove(contributor);
        if (contributorList.isEmpty()) {
			contributors.remove(targetType);
		}
        flushLookup();
    }


    public void unregisterContributors(String targetType) {
        contributors.remove(targetType);
        flushLookup();
    }
    
    protected List getContributors(Object object) {
    	Object resource  = LegacyResourceSupport.getAdaptedContributorResource(object);	
    	
    	List adapters = new ArrayList(Arrays.asList(Platform.getAdapterManager().computeAdapterTypes(object.getClass())));
    	removeCommonAdapters(adapters, Arrays.asList(new Class[] {object.getClass()}));

    	List contributors = new ArrayList();
        
        addAll(contributors, getObjectContributors(object.getClass()));
        if(resource != null) {
			addAll(contributors, getResourceContributors(resource.getClass()));
		}
    	if(adapters != null) {
    		for (Iterator it = adapters.iterator(); it.hasNext();) {
				String adapter = (String) it.next();				
				addAll(contributors, getAdaptableContributors(adapter));
			}
    	}
    	
        contributors = removeDups(contributors);

    	return contributors.isEmpty() ? Collections.EMPTY_LIST : new ArrayList(contributors);
    }
    
    protected List getObjectContributors(Class objectClass) {
		List objectList = null;
		if (objectLookup != null) {
			objectList = (List) objectLookup.get(objectClass);
		}
		if (objectList == null) {
			objectList = addContributorsFor(objectClass);
			if (objectList.size() == 0) {
				objectList = Collections.EMPTY_LIST;
			}
			else {
				objectList = Collections.unmodifiableList(objectList);
			}
			cacheObjectLookup(objectClass, objectList);
		}
		return objectList;
	}

	protected List getResourceContributors(Class resourceClass) {
		List resourceList = null;
		if (resourceAdapterLookup != null) {
			resourceList = (List) resourceAdapterLookup.get(resourceClass);
		}
		if (resourceList == null) {
			resourceList = addContributorsFor(resourceClass);
			if (resourceList.size() == 0) {
				resourceList = Collections.EMPTY_LIST;
			} else {
				resourceList = Collections.unmodifiableList(filterOnlyAdaptableContributors(resourceList));
			}
			cacheResourceAdapterLookup(resourceClass, resourceList);
		}
		return resourceList;
	}

	protected List getAdaptableContributors(String adapterType) {
		List adaptableList = null;
		if (adaptableLookup != null) {
			adaptableList = (List) adaptableLookup.get(adapterType);
		}
		if (adaptableList == null) {
			if (LegacyResourceSupport.isResourceType(adapterType) || LegacyResourceSupport.isResourceMappingType(adapterType)) {
				adaptableList = Collections.EMPTY_LIST;
			}
			else {
				adaptableList = (List) contributors.get(adapterType);
				if (adaptableList == null || adaptableList.size() == 0) {
					adaptableList = Collections.EMPTY_LIST;
				} else {
					adaptableList = Collections.unmodifiableList(filterOnlyAdaptableContributors(adaptableList));
				}
			}
			cacheAdaptableLookup(adapterType, adaptableList);
		}
		return adaptableList;
	}
	
	protected void removeCommonAdapters(List adapters, List results) {
    	for (Iterator it = results.iterator(); it.hasNext();) {
			Class clazz = ((Class) it.next());
			List commonTypes = computeCombinedOrder(clazz);
			for (Iterator it2 = commonTypes.iterator(); it2.hasNext();) {
				Class type = (Class) it2.next();
				adapters.remove(type.getName());	
			}				
		}
    }
	
    protected List computeCombinedOrder(Class inputClass) {
        List result = new ArrayList(4);
        Class clazz = inputClass;
        while (clazz != null) {
            result.add(clazz);
            Class[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                result.add(interfaces[i]);
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

	private List filterOnlyAdaptableContributors(List contributors) {
		List adaptableContributors = null;
		for (Iterator it = contributors.iterator(); it.hasNext();) {
			IObjectContributor c = (IObjectContributor) it.next();
			if(c.canAdapt()) {
				if(adaptableContributors == null) {
					adaptableContributors = new ArrayList();
				}
				adaptableContributors.add(c);
			}
		}
		return adaptableContributors == null ? Collections.EMPTY_LIST : adaptableContributors;
	}
	
    @Override
	public void removeExtension(IExtension source, Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof ContributorRecord) {
                ContributorRecord contributorRecord = (ContributorRecord) objects[i];
                unregisterContributor((contributorRecord).contributor, (contributorRecord).objectClassName);
                contributorRecordSet.remove(contributorRecord);
            }
        }
    }

    public void dispose() {
    	if(getExtensionPointFilter() != null) {
			PlatformUI.getWorkbench().getExtensionTracker().unregisterHandler(this);
		}
    }
    
    protected List getContributors(List elements) {
        List commonAdapters = new ArrayList();
        List commonClasses = getCommonClasses(elements, commonAdapters);
        
        Class resourceClass = getCommonResourceClass(elements);
        Class resourceMappingClass = getResourceMappingClass(elements);

        
        List contributors = new ArrayList();
        
        if (resourceClass != null) {
            addAll(contributors, getResourceContributors(resourceClass));
        }
        if (commonClasses != null && !commonClasses.isEmpty()) {
            for (int i = 0; i < commonClasses.size(); i++) {
                List results = getObjectContributors((Class) commonClasses
                        .get(i));
                addAll(contributors, results);
            }
        }
        if (resourceMappingClass == null) {
            resourceMappingClass = LegacyResourceSupport
                    .getResourceMappingClass();
            if (resourceMappingClass != null
                    && commonAdapters.contains(resourceMappingClass.getName())) {
            	addAll(contributors, getResourceContributors(resourceMappingClass));
            }
        } else {
            contributors.addAll(getResourceContributors(resourceMappingClass));
        }
        if (!commonAdapters.isEmpty()) {
            for (Iterator it = commonAdapters.iterator(); it.hasNext();) {
                String adapter = (String) it.next();
                addAll(contributors, getAdaptableContributors(adapter));
            }
        }
    	
        contributors = removeDups(contributors);
        
        return contributors.isEmpty() ? Collections.EMPTY_LIST : new ArrayList(contributors);
    }

	private static void addAll(Collection collection, List toAdd) {
		for (int i = 0, size = toAdd.size(); i < size; ++i) {
			collection.add(toAdd.get(i));
		}
	}

    private static List removeDups(List list) {
    	if (list.size() <= 1) {
    		return list;
    	}
    	HashSet set = new HashSet(list);
    	if (set.size() == list.size()) {
    		return list;
    	}
    	ArrayList result = new ArrayList(set.size());
    	for (Iterator i = list.iterator(); i.hasNext();) {
    		Object o = i.next();
    		if (set.remove(o)) {
    			result.add(o);
    		}
		}
    	return result;
    }
    
    private List getCommonClasses(List objects, List commonAdapters) {
        if (objects == null || objects.size() == 0) {
			return null;
		}

        if (allSameClass(objects)) {
        	
        	Class clazz = objects.get(0).getClass();
        	commonAdapters.addAll(Arrays.asList(Platform.getAdapterManager().computeAdapterTypes(clazz)));
        	List result = new ArrayList(1);
        	result.add(clazz);
        	return result;
        }
        
        List classes = computeClassOrder(objects.get(0).getClass());
        List adapters = computeAdapterOrder(classes);
        List interfaces = computeInterfaceOrder(classes);

        List lastCommonTypes = new ArrayList();

        boolean classesEmpty = classes.isEmpty();
        boolean interfacesEmpty = interfaces.isEmpty();

        for (int i = 1; i < objects.size(); i++) {
            List otherClasses = computeClassOrder(objects.get(i).getClass());
            if (!classesEmpty) {
                classesEmpty = extractCommonClasses(classes, otherClasses);
            }

            List otherInterfaces = computeInterfaceOrder(otherClasses);
            if (!interfacesEmpty) {
                interfacesEmpty = extractCommonClasses(interfaces,
                        otherInterfaces);
            }

            List classesAndInterfaces = new ArrayList(otherClasses);
            if (otherInterfaces != null) {
				classesAndInterfaces.addAll(otherInterfaces);
			}
            List otherAdapters = computeAdapterOrder(classesAndInterfaces);

            if (otherAdapters.isEmpty() && !adapters.isEmpty()) {
                removeNonCommonAdapters(adapters, classesAndInterfaces);
            } else {
                if (adapters.isEmpty()) {
                    removeNonCommonAdapters(otherAdapters, lastCommonTypes);
                    if (!otherAdapters.isEmpty()) {
						adapters.addAll(otherAdapters);
					}
                } else {
                    for (Iterator it = adapters.iterator(); it.hasNext();) {
                        String adapter = (String) it.next();
                        if (!otherAdapters.contains(adapter)) {
                            it.remove();
                        }
                    }
                }
            }

            lastCommonTypes.clear();
            lastCommonTypes.addAll(classes);
            lastCommonTypes.addAll(interfaces);

            if (interfacesEmpty && classesEmpty && adapters.isEmpty()) {
                return null;
            }
        }

        ArrayList results = new ArrayList(4);
        ArrayList superClasses = new ArrayList(4);
        if (!classesEmpty) {
            for (int j = 0; j < classes.size(); j++) {
                if (classes.get(j) != null) {
                    superClasses.add(classes.get(j));
                }
            }
            if (!superClasses.isEmpty()) {
                results.add(superClasses.get(0));
            }
        }

        if (!interfacesEmpty) {
            removeCommonInterfaces(superClasses, interfaces, results);
        }

        if (!adapters.isEmpty()) {
            removeCommonAdapters(adapters, results);
            commonAdapters.addAll(adapters);
        }
        return results;
    }

	private boolean allSameClass(List objects) {
		int size = objects.size();
		if (size <= 1) return true;
		Class clazz = objects.get(0).getClass();
		for (int i = 1; i < size; ++i) {
			if (!objects.get(i).getClass().equals(clazz)) {
				return false;
			}
		}
		return true;
	}

	private boolean extractCommonClasses(List classes, List otherClasses) {
        boolean classesEmpty = true;
        if (otherClasses.isEmpty()) {
            classes.clear();
        } else {
            for (int j = 0; j < classes.size(); j++) {
                if (classes.get(j) != null) {
                    classesEmpty = false; // TODO: should this only be set if item not nulled out?
                    if (!otherClasses.contains(classes.get(j))) {
                        classes.set(j, null);
                    }
                }
            }
        }
        return classesEmpty;
    }

    private void removeNonCommonAdapters(List adapters, List classes) {
        for (int i = 0; i < classes.size(); i++) {
            Object o = classes.get(i);
            if (o != null) {
                Class clazz = (Class) o;
                String name = clazz.getName();
                if (adapters.contains(name)) {
					return;
				}
            }
        }
        adapters.clear();
    }

    private void removeCommonInterfaces(List superClasses, List types,
            List results) {
        List dropInterfaces = null;
        if (!superClasses.isEmpty()) {
            dropInterfaces = computeInterfaceOrder(superClasses);
        }
        for (int j = 0; j < types.size(); j++) {
            if (types.get(j) != null) {
                if (dropInterfaces != null
                        && !dropInterfaces.contains(types.get(j))) {
                    results.add(types.get(j));
                }
            }
        }
    }

    private List computeAdapterOrder(List classList) {
        Set result = new HashSet(4);
        IAdapterManager adapterMgr = Platform.getAdapterManager();
        for (Iterator list = classList.iterator(); list.hasNext();) {
            Class clazz = ((Class) list.next());
            String[] adapters = adapterMgr.computeAdapterTypes(clazz);
            for (int i = 0; i < adapters.length; i++) {
                String adapter = adapters[i];
                if (!result.contains(adapter)) {
                    result.add(adapter);
                }
            }
        }
        return new ArrayList(result);
    }

    private Class getCommonResourceClass(List objects) {
        if (objects == null || objects.size() == 0) {
            return null;
        }
        Class resourceClass = LegacyResourceSupport.getResourceClass();
        if (resourceClass == null) {
            return null;
        }

        List testList = new ArrayList(objects.size());

        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);

            if (object instanceof IAdaptable) {
                if (resourceClass.isInstance(object)) {
                    continue;
                }

                Object resource = LegacyResourceSupport
                        .getAdaptedContributorResource(object);

                if (resource == null) {
                    return null;
                }
                testList.add(resource);
            } else {
                return null;
            }
        }

        return getCommonClass(testList);
    }

    private Class getResourceMappingClass(List objects) {
        if (objects == null || objects.size() == 0) {
            return null;
        }
        Class resourceMappingClass = LegacyResourceSupport
                .getResourceMappingClass();
        if (resourceMappingClass == null) {
            return null;
        }

        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);

            if (object instanceof IAdaptable) {
                if (resourceMappingClass.isInstance(object)) {
                    continue;
                }

                Object resourceMapping = LegacyResourceSupport
                        .getAdaptedContributorResourceMapping(object);

                if (resourceMapping == null) {
                    return null;
                }
            } else {
                return null;
            }
        }
        return resourceMappingClass;
    }

    private Class getCommonClass(List objects) {
        if (objects == null || objects.size() == 0) {
			return null;
		}
        Class commonClass = objects.get(0).getClass();
        if (objects.size() == 1) {
			return commonClass;
		}

        for (int i = 1; i < objects.size(); i++) {
            Object object = objects.get(i);
            Class newClass = object.getClass();
            if (newClass.equals(commonClass)) {
				continue;
			}
            commonClass = getCommonClass(commonClass, newClass);
            if (commonClass == null) {
				return null;
			}
        }
        return commonClass;
    }

    private Class getCommonClass(Class class1, Class class2) {
        List list1 = computeCombinedOrder(class1);
        List list2 = computeCombinedOrder(class2);
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                Class candidate1 = (Class) list1.get(i);
                Class candidate2 = (Class) list2.get(j);
                if (candidate1.equals(candidate2)) {
					return candidate1;
				}
            }
        }
        return null;
    }
}
