package org.eclipse.jface.resource;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.pde.api.tools.annotations.NoReference;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

@NoReference
public class LazyResourceManager extends ResourceManager {
	private static class LruMap extends LinkedHashMap<DeviceResourceDescriptor, ResourceManager> {
		private static final long serialVersionUID = 1L;
		int cacheSize;

		LruMap(int cacheSize) {
			super(cacheSize, 0.75f, true); // last access-order
			this.cacheSize = cacheSize;
		}

		@Override
		protected boolean removeEldestEntry(Entry<DeviceResourceDescriptor, ResourceManager> eldest) {
			boolean remove = size() > cacheSize;
			if (remove) {
				eldest.getValue().destroy(eldest.getKey());
			}
			return remove;
		}
	}

	private final ResourceManager parent;
	private LruMap leastRecentlyUsed;

	public LazyResourceManager(int cacheSize, ResourceManager parent) {
		this.parent = parent;
		this.leastRecentlyUsed = new LruMap(cacheSize);
	}

	@Override
	public Device getDevice() {
		return parent.getDevice();
	}

	@Override
	public Object create(DeviceResourceDescriptor descriptor) {
		LruMap lruMap = leastRecentlyUsed;
		if (lruMap != null) {
			lruMap.get(descriptor); // update least recently used;
		}
		return parent.create(descriptor);
	}

	@Override
	public void destroy(DeviceResourceDescriptor descriptor) {
		leastRecentlyUsed.put(descriptor, parent); // destroyed
	}

	@Override
	protected Image getDefaultImage() {
		return parent.getDefaultImage();
	}

	@Override
	public Object find(DeviceResourceDescriptor descriptor) {
		return parent.find(descriptor);
	}

}
