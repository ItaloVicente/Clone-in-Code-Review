
package org.eclipse.ui.internal.layout;


public class TrimDescriptor {

	private IWindowTrim fTrim;

	private SizeCache fCache;

	private SizeCache fDockingHandle = null;

	private int fAreaId;

	public TrimDescriptor(IWindowTrim trim, int areaId) {
		fTrim = trim;
		fAreaId = areaId;
	}

	public SizeCache getCache() {
		return fCache;
	}

	public void setCache(SizeCache c) {
		fCache = c;
	}

	public IWindowTrim getTrim() {
		return fTrim;
	}

	public SizeCache getDockingCache() {
		return fDockingHandle;
	}

	public String getId() {
		return fTrim.getId();
	}

	public boolean isVisible() {
		if (!fTrim.getControl().isDisposed()) {
			return fTrim.getControl().isVisible();
		}
		return false;
	}

	public void setDockingCache(SizeCache cache) {
		fDockingHandle = cache;
	}

	public int getAreaId() {
		return fAreaId;
	}

	public void setAreaId(int id) {
		fAreaId = id;
	}

	public void flush() {
		if (fCache != null) {
			fCache.flush();
		}
		if (fDockingHandle != null) {
			fDockingHandle.flush();
		}
	}

	public void setVisible(boolean visible) {
		if (fTrim.getControl() != null && !fTrim.getControl().isDisposed()) {
			fTrim.getControl().setVisible(visible);
		}
		if (fDockingHandle != null && fDockingHandle.getControl() != null
				&& !fDockingHandle.getControl().isDisposed()) {
			fDockingHandle.getControl().setVisible(visible);
		}
	}
}
