/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Abstract implementation of ResourceManager. Maintains reference counts for all previously
 * allocated SWT resources. Delegates to the abstract method allocate(...) the first time a resource
 * is referenced and delegates to the abstract method deallocate(...) the last time a reference is
 * removed.
 *
 * @since 3.1
 */
abstract class AbstractResourceManager extends ResourceManager {

    /**
     * Map of ResourceDescriptor onto RefCount. (null when empty)
     */
    private HashMap<DeviceResourceDescriptor, RefCount> map = null;

    /**
     * Holds a reference count for a previously-allocated resource
     */
    private static class RefCount {
        Object resource;
        int count = 1;

        RefCount(Object resource) {
            this.resource = resource;
        }
    }

    /**
     * Called the first time a resource is requested. Should allocate and return a resource
     * of the correct type.
     *
     * @since 3.1
     *
     * @param descriptor identifier for the resource to allocate
     * @return the newly allocated resource
     * @throws DeviceResourceException Thrown when allocation of an SWT device resource fails
     */
    protected abstract Object allocate(DeviceResourceDescriptor descriptor) throws DeviceResourceException;

    /**
     * Called the last time a resource is dereferenced. Should release any resources reserved by
     * allocate(...).
     *
     * @since 3.1
     *
     * @param resource resource being deallocated
     * @param descriptor identifier for the resource
     */
    protected abstract void deallocate(Object resource, DeviceResourceDescriptor descriptor);

    @Override
	public final Object create(DeviceResourceDescriptor descriptor) throws DeviceResourceException {

        if (map == null) {
            map = new HashMap<>();
        }

        RefCount count = map.get(descriptor);
        if (count != null) {
            count.count++;
            return count.resource;
        }

        Object resource = allocate(descriptor);

        count = new RefCount(resource);
        map.put(descriptor, count);

        return resource;
    }

    @Override
	public final void destroy(DeviceResourceDescriptor descriptor) {
        if (map == null) {
            return;
        }

        RefCount count = map.get(descriptor);
        if (count != null) {
            count.count--;
            if (count.count == 0) {
                deallocate(count.resource, descriptor);
                map.remove(descriptor);
            }
        }

        if (map.isEmpty()) {
            map = null;
        }
    }

    /**
     * Deallocates any resources allocated by this registry that have not yet been
     * deallocated.
     *
     * @since 3.1
     */
    @Override
	public void dispose() {
        super.dispose();

        if (map == null) {
            return;
        }

        Collection<Entry<DeviceResourceDescriptor, RefCount>> entries = map.entrySet();

        for (Entry<DeviceResourceDescriptor, RefCount> next : entries) {
            Object key = next.getKey();
            RefCount val = next.getValue();

            deallocate(val.resource, (DeviceResourceDescriptor)key);
        }

        map = null;
    }

    @Override
	public Object find(DeviceResourceDescriptor descriptor) {
        if (map == null) {
            return null;
        }
        RefCount refCount = map.get(descriptor);
        if (refCount == null)
        	return null;
		return refCount.resource;
    }
}
