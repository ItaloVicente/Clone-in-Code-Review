
package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;

public class CheckStateProviderTestsUtil {
	private static final int NUMBER_OF_STATES = 4;

	public static class TestMethodsInvokedCheckStateProvider implements ICheckStateProvider {
		public List isCheckedInvokedOn = new ArrayList();
		public List isGrayedInvokedOn = new ArrayList();
		
		@Override
		public boolean isChecked(Object element) {
			isCheckedInvokedOn.add(element);
			return true;
		}

		@Override
		public boolean isGrayed(Object element) {
			isGrayedInvokedOn.add(element);
			return true;
		}
		
		public void reset() {
			isCheckedInvokedOn = new ArrayList();
			isGrayedInvokedOn = new ArrayList();
		}
	}
	
	public static final class TestCheckStateProvider extends TestMethodsInvokedCheckStateProvider {
		private int shift;
		
		public TestCheckStateProvider(int shift) {
			this.shift = shift;
		}
		
		@Override
		public boolean isChecked(Object element) {
			super.isChecked(element);
			return shouldBeChecked((TestElement)element, shift);
		}

		@Override
		public boolean isGrayed(Object element) {
			super.isGrayed(element);
			return shouldBeGrayed((TestElement)element, shift);
		}
	}
	
	public static final class Sorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return constructNumber((TestElement)e1) - constructNumber((TestElement)e2);
		}
	}
	
	public static final class Filter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			return (constructNumber((TestElement)element) % (NUMBER_OF_STATES * 2 - 1)) == (NUMBER_OF_STATES - 1);
		}
	}
	public static int constructNumber(TestElement te) {
		String id = te.getID();
		int number = Integer.parseInt(id.substring(id.lastIndexOf('-') + 1)) + id.length();
		return number % NUMBER_OF_STATES;
	}
	
	public static boolean shouldBeChecked(TestElement te, int shift) {
		return ((constructNumber(te) + shift) % NUMBER_OF_STATES) > 1;
	}
	
	public static boolean shouldBeGrayed(TestElement te, int shift) {
		return ((constructNumber(te) + shift) % NUMBER_OF_STATES) % 2 == 1;
	}
}
