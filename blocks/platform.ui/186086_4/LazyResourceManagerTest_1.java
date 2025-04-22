package org.eclipse.jface.resource;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

public class LazyResourceManager extends ResourceManager {

	private final static ReferenceQueue<LruMap> disposeQueue = new ReferenceQueue<>();
	private static SoftReference<LruMap> leastRecentlyUsed;

	static class LruMap extends LinkedHashMap<DeviceResourceDescriptor, ResourceManager> {
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
	private int cacheSize;

	public LazyResourceManager(int cacheSize, ResourceManager parent) {
		this.parent = parent;
		this.cacheSize = cacheSize;
	}

	@Override
	public Device getDevice() {
		return parent.getDevice();
	}

	@Override
	public Object create(DeviceResourceDescriptor descriptor) {
		cleanDisposeQueue();
		LruMap lruMap = leastRecentlyUsed == null ? null : leastRecentlyUsed.get();
		if (lruMap != null) {
			lruMap.get(descriptor); // update least recently used;
		}
		return parent.create(descriptor);
	}

	private static void cleanDisposeQueue() {
		for (Reference<? extends LruMap> ref = disposeQueue.poll(); ref != null; ref = disposeQueue.poll()) {
			LruMap lru = ref.get();
			lru.forEach((d, p) -> p.destroy(d));
		}
	}

	@Override
	public void destroy(DeviceResourceDescriptor descriptor) {
		cleanDisposeQueue();
		LruMap lruMap = leastRecentlyUsed == null ? null : leastRecentlyUsed.get();
		if (lruMap == null) {
			lruMap = new LruMap(cacheSize);
			leastRecentlyUsed = new SoftReference<>(lruMap, disposeQueue);
		}
		lruMap.put(descriptor, parent); // destroyed
	}

	@Override
	protected Image getDefaultImage() {
		cleanDisposeQueue();
		return parent.getDefaultImage();
	}

	@Override
	public Object find(DeviceResourceDescriptor descriptor) {
		cleanDisposeQueue();
		return parent.find(descriptor);
	}

}
