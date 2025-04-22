
package org.eclipse.ui.tests.markers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.eclipse.ui.views.markers.MarkerSupportView;
import org.eclipse.ui.views.markers.internal.MarkerSupportRegistry;

public class MarkersTestMarkersView extends MarkerSupportView {

	private Tree tree;

	public MarkersTestMarkersView() {
		super(MarkerSupportRegistry.PROBLEMS_GENERATOR);
	}

	public IMarker[] getCurrentMarkers() {
		Method method;
		try {
			method = ExtendedMarkersView.class.getDeclaredMethod("getAllMarkers",
					new Class[0]);
			method.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
			return new IMarker[0];
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return new IMarker[0];
		}
		try {
			return (IMarker[]) method.invoke(this, new Object[0]);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new IMarker[0];
	}

	public void addUpdateFinishListener(IJobChangeListener listener) {
		getUpdateJobForListener().addJobChangeListener(listener);

	}

	private Job getUpdateJobForListener() {
		Field field;
		try {
			field = ExtendedMarkersView.class.getDeclaredField("updateJob");
			field.setAccessible(true);
			return (Job) field.get(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void removeUpdateFinishListener(IJobChangeListener listener) {
		getUpdateJobForListener().addJobChangeListener(listener);

	}

	public void setColumnWidths(int width) {
		TreeColumn[] treeColumns = tree.getColumns();
		for (int j = 0; j < treeColumns.length; j++) {
			treeColumns[j].setWidth(width);
		}
		
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		tree = (Tree) parent.getChildren()[0];
	}

	public boolean checkColumnSizes(int size) {
		TreeColumn[] treeColumns = tree.getColumns();
		
		for (int j = 0; j < treeColumns.length - 1; j++) {
			if(treeColumns[j].getWidth() == size)
				continue;
			return false;
		}
		return true;
	}

}
