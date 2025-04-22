package org.eclipse.ui.internal.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;

public class TrimLayout extends Layout implements ICachingLayout, ITrimManager {

	public static final Integer TOP_ID = new Integer(TOP);

	public static final Integer BOTTOM_ID = new Integer(BOTTOM);

	public static final Integer LEFT_ID = new Integer(LEFT);

	public static final Integer RIGHT_ID = new Integer(RIGHT);

	public static final Integer NONTRIM_ID = new Integer(NONTRIM);

	private static final int[] TRIM_ID_INFO = { LEFT, RIGHT, TOP, BOTTOM };

	private SizeCache centerArea = new SizeCache();

	private Map fTrimArea = new HashMap();

	private Map fTrimDescriptors = new HashMap();
	
	private boolean trimLocked;

	private HashMap preferredLocationMap = new HashMap();

	public TrimLayout() {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
        trimLocked = store.getBoolean(IWorkbenchPreferenceConstants.LOCK_TRIM);
		
		createTrimArea(TOP_ID, TOP_ID.toString());
		createTrimArea(BOTTOM_ID, BOTTOM_ID.toString());
		createTrimArea(LEFT_ID, LEFT_ID.toString());
		createTrimArea(RIGHT_ID, RIGHT_ID.toString());
	}

	private void createTrimArea(Integer id, String displayName) {
		TrimArea top = new TrimArea(id.intValue(), displayName);
		fTrimArea.put(id, top);
	}

	public int getTrimAreaId(Control trimControl) {
		TrimDescriptor desc = findTrimDescription(trimControl);
		if (desc != null) {
			return desc.getAreaId();
		}
		return SWT.DEFAULT;
	}

	@Deprecated
	public void addTrim(IWindowTrim control, int areaId) {
		addTrim(areaId, control, null);
	}

	@Deprecated
	public void addTrim(IWindowTrim trim, int areaId, IWindowTrim beforeMe) {
		addTrim(areaId, trim, beforeMe);
	}

	@Override
	public void addTrim(int areaId, IWindowTrim trim) {
		IWindowTrim insertBefore = null;
		List trimDescs = getAreaTrim(areaId);
		for (Iterator trimIter = trimDescs.iterator(); trimIter.hasNext();) {
			IWindowTrim curTrim = (IWindowTrim) trimIter.next();
			if (curTrim.getId().equals(trim.getId())) {
				if (trimIter.hasNext()) {
					insertBefore = (IWindowTrim) trimIter.next();
				}
			}
		}
		
		addTrim(areaId, trim, insertBefore);
	}
	
	@Override
	public void addTrim(int areaId, IWindowTrim trim, IWindowTrim beforeMe) {
		TrimArea area = (TrimArea) fTrimArea.get(new Integer(areaId));
		if (area == null) {
			return;
		}
		
		removeTrim(trim);

		TrimDescriptor desc = new TrimDescriptor(trim, areaId);
		
		boolean isAlreadyAHandle = trim instanceof TrimToolBarBase;
		if (!trimLocked && trim.getValidSides() != SWT.NONE && !isAlreadyAHandle) {
			Composite dockingHandle = new TrimCommonUIHandle(this, trim, areaId);
			desc.setDockingCache(new SizeCache(dockingHandle));
		}

		SizeCache cache = new SizeCache(trim.getControl());
		trim.getControl().setLayoutData(trim);
		desc.setCache(cache);
		
		trim.getControl().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Control control = (Control) e.widget;
				if (control.getLayoutData() instanceof IWindowTrim) {
					IWindowTrim trim = (IWindowTrim) control.getLayoutData();
					removeTrim(trim);
				}
			}
		});

		fTrimDescriptors.put(desc.getId(), desc);

		if (beforeMe != null) {
			TrimDescriptor beforeDesc = (TrimDescriptor) fTrimDescriptors
					.get(beforeMe.getId());
			if (beforeDesc != null && beforeDesc.getAreaId() == areaId) {
				area.addTrim(desc, beforeDesc);
			} else {
				area.addTrim(desc);
			}
		} else {
			area.addTrim(desc);
		}
	}

	@Override
	public void forceLayout() {
		removeDisposed();

		Iterator d = fTrimDescriptors.values().iterator();
		while (d.hasNext()) {
			TrimDescriptor desc = (TrimDescriptor) d.next();
			if (desc.getTrim().getControl() != null) {
				LayoutUtil.resize(desc.getTrim().getControl());
				return;
			}
		}
	}

	@Override
	public void removeTrim(IWindowTrim toRemove) {
		TrimDescriptor desc = (TrimDescriptor) fTrimDescriptors.remove(toRemove
				.getId());
		if (desc == null) {
			return;
		}

		TrimArea area = (TrimArea) fTrimArea.get(new Integer(desc.getAreaId()));
		if (area != null) {
			area.removeTrim(desc);
			desc.getCache().getControl().setLayoutData(null);
		}

		if (desc.getDockingCache() != null) {
			Control ctrl = desc.getDockingCache().getControl();
			
			ctrl.setVisible(false);
			desc.setDockingCache(null);
		}
	}

	@Override
	public IWindowTrim getTrim(String id) {
		TrimDescriptor desc = (TrimDescriptor) fTrimDescriptors.get(id);
		if (desc != null) {
			return desc.getTrim();
		}
		return null;
	}

	private void removeDisposed() {
		Iterator a = fTrimArea.values().iterator();
		while (a.hasNext()) {
			TrimArea area = (TrimArea) a.next();
			Iterator d = area.getDescriptors().iterator();
			while (d.hasNext()) {
				TrimDescriptor desc = (TrimDescriptor) d.next();
				Control nextControl = desc.getTrim().getControl();
				if (nextControl == null || nextControl.isDisposed()) {
					area.removeTrim(desc);
					
					fTrimDescriptors.remove(desc.getId());
				}
			}
		}
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint,
			boolean flushCache) {
		return new Point(0, 0);
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		removeDisposed();

		TrimArea top = (TrimArea) fTrimArea.get(TOP_ID);
		TrimArea bottom = (TrimArea) fTrimArea.get(BOTTOM_ID);
		TrimArea left = (TrimArea) fTrimArea.get(LEFT_ID);
		TrimArea right = (TrimArea) fTrimArea.get(RIGHT_ID);

		Rectangle clientArea = composite.getClientArea();

		int trim_top = top.computeWrappedTrim(clientArea.width);
		int trim_bottom = bottom.computeWrappedTrim(clientArea.width);
		
		int verticalMajor = clientArea.height- (trim_top+trim_bottom);
		
		int trim_left = left.computeWrappedTrim(verticalMajor);
		int trim_right = right.computeWrappedTrim(verticalMajor);
		
		top.tileTrim(clientArea.x, clientArea.y, clientArea.width);
		bottom.tileTrim(clientArea.x, clientArea.height-trim_bottom, clientArea.width);
		left.tileTrim(clientArea.x, clientArea.y + trim_top, verticalMajor);
		right.tileTrim(clientArea.width-trim_right, clientArea.y + trim_top, verticalMajor);

		if (centerArea.getControl() != null) {
			Control caCtrl = centerArea.getControl();
			caCtrl.setBounds(clientArea.x+trim_left,
					clientArea.y+trim_top,
					clientArea.width - (trim_left+trim_right),
					clientArea.height - (trim_top+trim_bottom));
		}
	}

	public void setCenterControl(Control center) {
		centerArea.setControl(center);
	}

	public Control getCenterControl() {
		return centerArea.getControl();
	}

	@Override
	public void flush(Control dirtyControl) {
		if (dirtyControl == centerArea.getControl()) {
			centerArea.flush();
		} else {
			TrimDescriptor desc = findTrimDescription(dirtyControl);
			if (desc != null) {
				desc.flush();
			}
		}
	}

	@Override
	public int[] getAreaIds() {
		return (int[]) TRIM_ID_INFO.clone();
	}

	@Override
	public List getAreaTrim(int areaId) {
		TrimArea area = (TrimArea) fTrimArea.get(new Integer(areaId));
		if (area == null) {
			return Collections.EMPTY_LIST;
		}
		return area.getTrims();
	}

	@Override
	public void updateAreaTrim(int id, List trim, boolean removeExtra) {
		TrimArea area = (TrimArea) fTrimArea.get(new Integer(id));
		if (area == null) {
			return;
		}
		List current = area.getTrims();

		Iterator i = trim.iterator();
		while (i.hasNext()) {
			IWindowTrim t = (IWindowTrim) i.next();
			t.dock(id);  // Ensure that the trim is properly oriented
			addTrim(id, t, null);
			current.remove(t);
		}

		if (removeExtra) {
			i = current.iterator();
			while (i.hasNext()) {
				IWindowTrim t = (IWindowTrim) i.next();
				removeTrim(t);
			}
		}
	}

	public Rectangle getTrimRect(Composite window, int areaId) {
		TrimArea area = getTrimArea(areaId);
		return window.getDisplay().map(window, null, area.getCurRect());
	}

	@Override
	public List getAllTrim() {
		List trimList = new ArrayList(fTrimDescriptors.size());

		Iterator d = fTrimDescriptors.values().iterator();
		while (d.hasNext()) {
			TrimDescriptor desc = (TrimDescriptor) d.next();
			trimList.add(desc.getTrim());
		}

		return trimList;
	}

	@Override
	public void setTrimVisible(IWindowTrim trim, boolean visible) {
		TrimDescriptor desc = findTrimDescription(trim.getControl());

		if (desc != null) {
			desc.setVisible(visible);
		}
	}

	private TrimDescriptor findTrimDescription(Control trim) {
		Iterator d = fTrimDescriptors.values().iterator();
		while (d.hasNext()) {
			TrimDescriptor desc = (TrimDescriptor) d.next();
			if (desc.getTrim().getControl() == trim) {
				return desc;
			}
			if (desc.getDockingCache() != null
					&& desc.getDockingCache().getControl() == trim) {
				return desc;
			}
		}
		return null;
	}

	public TrimArea getTrimArea(int areaId) {
		return (TrimArea) fTrimArea.get(new Integer(areaId));
	}

	public void setPreferredLocations(int areaId, List preferredLocations) {
		preferredLocationMap.put(new Integer(areaId), preferredLocations);
	}
	
	public int getPreferredArea(String trimId) {
		Iterator keyIter = preferredLocationMap.keySet().iterator();
		while (keyIter.hasNext()) {
			Integer key = (Integer) keyIter.next();
			List areaList = (List) preferredLocationMap.get(key);
			if (areaList.contains(trimId))
				return key.intValue();
		}
		
		return -1;
	}
	
	public IWindowTrim getPreferredLocation(String trimId) {
		Iterator keyIter = preferredLocationMap.keySet().iterator();
		while (keyIter.hasNext()) {
			Integer key = (Integer) keyIter.next();
			List areaList = (List) preferredLocationMap.get(key);
			int index = areaList.indexOf(trimId);
			if (index != -1) {
				for (int i = index+1; i < areaList.size(); i++) {
					String id = (String) areaList.get(i);
					IWindowTrim trim = getTrim(id);
					if (trim != null)
						return trim;
				}
			}
		}
		
		return null;
	}

	public List disableTrim(IWindowTrim ignoreMe) {
		List disabledControls = new ArrayList();

		List allTrim = getAllTrim();
		for (Iterator trimIter = allTrim.iterator(); trimIter.hasNext();) {
			IWindowTrim trim = (IWindowTrim) trimIter.next();
			if (ignoreMe == trim)
				continue;
			
			Control ctrl = trim.getControl();
			if (ctrl == null || ctrl.isDisposed() || !ctrl.isVisible() || !ctrl.isEnabled())
				continue;
			
			ctrl.setEnabled(false);
			disabledControls.add(ctrl);
		}
		
		return disabledControls;
	}
	
	public void enableTrim(List disabledControls) {
		for (Iterator dcIter = disabledControls.iterator(); dcIter.hasNext();) {
			Control ctrl = (Control) dcIter.next();
			
			if (!ctrl.isDisposed() && !ctrl.isEnabled())
				ctrl.setEnabled(true);
		}
	}
}
