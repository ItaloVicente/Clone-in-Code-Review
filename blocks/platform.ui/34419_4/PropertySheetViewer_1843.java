package org.eclipse.ui.views.properties;

import java.text.Collator; // can't use ICU, in public API
import java.util.Arrays;
import java.util.Comparator;

public class PropertySheetSorter  {

	private Collator collator;

	public PropertySheetSorter() {
		this(Collator.getInstance());
	}

	public PropertySheetSorter(Collator collator) {
		this.collator = collator;
	}

	public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
		return getCollator().compare(entryA.getDisplayName(),
				entryB.getDisplayName());
	}

	public int compareCategories(String categoryA, String categoryB) {
		return getCollator().compare(categoryA, categoryB);
	}

	protected Collator getCollator() {
		return collator;
	}

	public void sort(IPropertySheetEntry[] entries) {
		Arrays.sort(entries, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return PropertySheetSorter.this.compare(
						(IPropertySheetEntry) a, (IPropertySheetEntry) b);
			}
		});
	}

	void sort(PropertySheetCategory[] categories) {
		Arrays.sort(categories, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return PropertySheetSorter.this.compareCategories(
						((PropertySheetCategory) a).getCategoryName(),
						((PropertySheetCategory) b).getCategoryName());
			}
		});
	}
}
