package org.eclipse.ui.internal;

public class TrimLayoutData {

	public static final int GROWABLE = 0x1;

	public static final int SHRINKABLE = 0x2;

	public static final int GRAB_EXCESS_MINOR = 0x4;

	public String trimId;

	public String areaId;

	public int flags;

	public ITrimAreaChangeListener listener;

	public int shrinkableSize;

	public TrimLayoutData(String trimId, String areaId, int flags,
			ITrimAreaChangeListener listener) {
		this.trimId = trimId;
		this.areaId = areaId;
		this.flags = flags;
		this.listener = listener;
	}

	public TrimLayoutData(String trimId, String areaId, int flags) {
		this(trimId, areaId, flags, null);
	}

	public TrimLayoutData(String trimId, String areaId) {
		this(trimId, areaId, 0);
	}

	public void setAreaId(String newAreaId) {
		if (listener != null) {
			listener.areaChanged(WorkbenchLayout.getOrientation(areaId),
					WorkbenchLayout.getOrientation(newAreaId));
		}

		areaId = newAreaId;
	}
}
