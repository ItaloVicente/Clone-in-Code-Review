package org.eclipse.ui.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

public final class LegacyResourceSupport {

	private static String[] resourceClassNames = {
        "org.eclipse.core.resources.IResource", //$NON-NLS-1$
        "org.eclipse.core.resources.IContainer", //$NON-NLS-1$
        "org.eclipse.core.resources.IFolder", //$NON-NLS-1$
        "org.eclipse.core.resources.IProject", //$NON-NLS-1$
        "org.eclipse.core.resources.IFile", //$NON-NLS-1$
	};
	
    private static Class iresourceClass = null;

    private static Class ifileClass;
    
    private static Class icontributorResourceAdapterClass = null;

    private static Method getAdaptedResourceMethod = null;
    
    private static Method getAdaptedResourceMappingMethod = null;
    
    private static Class icontributorResourceAdapter2Class = null;
    
    private static Class defaultContributorResourceAdapterClass = null;

    private static Object defaultContributorResourceAdapter = null;
    
    private static Class resourceMappingClass = null;

    private static boolean resourceAdapterPossible = true;


    public static Class getFileClass() {
        if (ifileClass != null) {
            return ifileClass;
        }
        Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.IFile"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            ifileClass = c;
        }
        return c;
    }

    public static Class getResourceClass() {
        if (iresourceClass != null) {
            return iresourceClass;
        }
        Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.IResource"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            iresourceClass = c;
        }
        return c;
    }

    public static Class getResourceMappingClass() {
        if (resourceMappingClass != null) {
            return resourceMappingClass;
        }
        Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.mapping.ResourceMapping"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            resourceMappingClass = c;
        }
        return c;
    }
    
    public static Class getIContributorResourceAdapterClass() {
        if (icontributorResourceAdapterClass != null) {
            return icontributorResourceAdapterClass;
        }
        Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.IContributorResourceAdapter"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            icontributorResourceAdapterClass = c;
        }
        return c;
    }

    public static Class getIContributorResourceAdapter2Class() {
        if (icontributorResourceAdapter2Class != null) {
            return icontributorResourceAdapter2Class;
        }
        Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.ide.IContributorResourceAdapter2"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            icontributorResourceAdapter2Class = c;
        }
        return c;
    }
    
    private static Class loadClass(String bundleName, String className) {
        if (!resourceAdapterPossible) {
            return null;
        }
        Bundle bundle = Platform.getBundle(bundleName);
        if (bundle == null) {
            resourceAdapterPossible = false;
            return null;
        }
        if (!BundleUtility.isActivated(bundle)) {
            resourceAdapterPossible = true;
            return null;
        }
        try {
            return bundle.loadClass(className);
        } catch (ClassNotFoundException e) {
            resourceAdapterPossible = false;
            return null;
        }
    }
    
    public static Class getDefaultContributorResourceAdapterClass() {
        if (defaultContributorResourceAdapterClass != null) {
            return defaultContributorResourceAdapterClass;
        }
        Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.internal.ide.DefaultContributorResourceAdapter"); //$NON-NLS-1$ //$NON-NLS-2$
        if (c != null) {
            defaultContributorResourceAdapterClass = c;
        }
        return c;
    }
    
    private static Object getDefaultContributorResourceAdapter() {
        if (defaultContributorResourceAdapter != null) {
            return defaultContributorResourceAdapter;
        }
        
		
			Class c = LegacyResourceSupport.getDefaultContributorResourceAdapterClass();
			if (c != null) {    			
				try {
					Method m  = c.getDeclaredMethod("getDefault", new Class[0]);//$NON-NLS-1$
					defaultContributorResourceAdapter = m.invoke(null, new Object[0]);
					return defaultContributorResourceAdapter;
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} 
    			
    			
			}
		
		return null;

    }
    
    public static boolean isResourceType(String objectClassName) {
        for (int i = 0; i < resourceClassNames.length; i++) {
            if (resourceClassNames[i].equals(objectClassName)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isResourceMappingType(String objectClassName) {
        return objectClassName.equals("org.eclipse.core.resources.mapping.ResourceMapping"); //$NON-NLS-1$
    }
    
    private static boolean isInstanceOf(Class clazz, String type) {
		if (clazz.getName().equals(type)) {
			return true;
		}
		Class superClass= clazz.getSuperclass();
		if (superClass != null && isInstanceOf(superClass, type)) {
			return true;
		}
		Class[] interfaces= clazz.getInterfaces();
		for (int i= 0; i < interfaces.length; i++) {
			if (isInstanceOf(interfaces[i], type)) {
				return true;
			}
		} 
		return false;
	}
    
    public static Object getAdaptedContributorResource(Object object) {
		Class resourceClass = LegacyResourceSupport.getResourceClass();
		if (resourceClass == null) {
			return null;
		}
		if (resourceClass.isInstance(object)) {
			return null;
		}
		if (object instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) object;
			Class contributorResourceAdapterClass = LegacyResourceSupport.getIContributorResourceAdapterClass();
			if (contributorResourceAdapterClass == null) {
				return adaptable.getAdapter(resourceClass);
			}
			Object resourceAdapter = adaptable.getAdapter(contributorResourceAdapterClass);
			if (resourceAdapter == null) {
			    resourceAdapter = LegacyResourceSupport.getDefaultContributorResourceAdapter();
			    if (resourceAdapter == null) {
					return null;
				}
			}
			
				Method m = getContributorResourceAdapterGetAdaptedResourceMethod();
				if (m != null) {
					try {
						return m.invoke(resourceAdapter, new Object[]{adaptable});
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
				}
			
		}
		return null;
	}
    
    private static Method getContributorResourceAdapterGetAdaptedResourceMethod() {
        if (getAdaptedResourceMethod != null) {
            return getAdaptedResourceMethod;
        }
      
        Class c = getIContributorResourceAdapterClass();
        if (c != null) {
            try {
				getAdaptedResourceMethod = c.getDeclaredMethod("getAdaptedResource", new Class[]{IAdaptable.class}); //$NON-NLS-1$
				return getAdaptedResourceMethod;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			} 
            	
        }
    	
    	return null;
	}


    private static Method getContributorResourceAdapter2GetAdaptedResourceMappingMethod() {
        if (getAdaptedResourceMappingMethod != null) {
            return getAdaptedResourceMappingMethod;
        }
       
        Class c = getIContributorResourceAdapter2Class();
        if (c != null) {
            try {
				getAdaptedResourceMappingMethod = c.getDeclaredMethod("getAdaptedResourceMapping", new Class[]{IAdaptable.class}); //$NON-NLS-1$
				return getAdaptedResourceMappingMethod;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			} 
            	
        }
    	
    	return null;
	}

    public static Object getAdaptedContributorResourceMapping(Object object) {
        Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
        if (resourceMappingClass == null) {
            return null;
        }
        if (resourceMappingClass.isInstance(object)) {
            return null;
        }
        if (object instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) object;
            Class contributorResourceAdapterClass = LegacyResourceSupport.getIContributorResourceAdapterClass();
            if (contributorResourceAdapterClass == null) {
                return adaptable.getAdapter(resourceMappingClass);
            }
            Class contributorResourceAdapter2Class = LegacyResourceSupport.getIContributorResourceAdapter2Class();
            if (contributorResourceAdapter2Class == null) {
                return adaptable.getAdapter(resourceMappingClass);
            }
            Object resourceAdapter = adaptable.getAdapter(contributorResourceAdapterClass);
            Object resourceMappingAdapter;
			if (resourceAdapter != null && contributorResourceAdapter2Class.isInstance(resourceAdapter)) {
            	resourceMappingAdapter = resourceAdapter;
            } else {
                resourceMappingAdapter = getDefaultContributorResourceAdapter();
                if (resourceMappingAdapter == null) {
                    return null;
                }
            }
            
           

	         Method m = getContributorResourceAdapter2GetAdaptedResourceMappingMethod();
	         if (m != null) {
	            	
				try {
					Object result  = m.invoke(resourceMappingAdapter, new Object[]{adaptable});
					if (result != null) {
		           		return result;
		           	}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
	            	
	         }
            
            
            Object r = getAdaptedContributorResource(object);
            if (r != null) {
            	return Platform.getAdapterManager().getAdapter(r, resourceMappingClass);
            }
            
            return null;
        }
        return Platform.getAdapterManager().getAdapter(object, resourceMappingClass);
    }
    
    public static IStructuredSelection adaptSelection(IStructuredSelection selection, String objectClass) {
		List newSelection = new ArrayList(10);
		for (Iterator it = selection.iterator(); it.hasNext();) {
			Object element = it.next();
			Object adaptedElement = getAdapter(element, objectClass);		
			if (adaptedElement != null) {
				newSelection.add(adaptedElement);
			}
		}
		return new StructuredSelection(newSelection);
	}
    
    public static Object getAdapter(Object element, String objectClass) {
		Object adaptedElement = null;
		if (isInstanceOf(element.getClass(), objectClass)) {
			adaptedElement = element;
		} else {		
			if (LegacyResourceSupport.isResourceType(objectClass)) {
				adaptedElement = getAdaptedResource(element);
            } else if (LegacyResourceSupport.isResourceMappingType(objectClass)) {
                adaptedElement = getAdaptedResourceMapping(element);
                if (adaptedElement == null) {
                    Object resource = getAdaptedResource(element);
                    if (resource != null) {
                        adaptedElement =( (IAdaptable)resource).getAdapter(LegacyResourceSupport.getResourceMappingClass());
                    }
                }
			} else {
				adaptedElement = Platform.getAdapterManager().loadAdapter(element, objectClass);
			}
		}
		return adaptedElement;
	}

    public static Object getAdaptedResource(Object element) {
		Class resourceClass = LegacyResourceSupport.getResourceClass();
		Object adaptedValue = null;
		if (resourceClass != null) {
			if (resourceClass.isInstance(element)) {
				adaptedValue = element;
			} else {
				adaptedValue = LegacyResourceSupport.getAdaptedContributorResource(element);
			}
		}
		return adaptedValue;
	}

    public static Object getAdaptedResourceMapping(Object element) {
        Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
        Object adaptedValue = null;
        if (resourceMappingClass != null) {
            if (resourceMappingClass.isInstance(element)) {
                adaptedValue = element;
            } else {
                adaptedValue = LegacyResourceSupport.getAdaptedContributorResourceMapping(element);
            }
        }
        return adaptedValue;
    }
    
    private LegacyResourceSupport() {
    }

}
