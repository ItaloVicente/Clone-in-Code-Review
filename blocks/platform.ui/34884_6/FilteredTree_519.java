package org.eclipse.ui.dialogs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StringMatcher;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.progress.WorkbenchJob;

public class FilteredList extends Composite {
	public interface FilterMatcher {
		void setFilter(String pattern, boolean ignoreCase,
				boolean ignoreWildCards);

		boolean match(Object element);
	}

	private class DefaultFilterMatcher implements FilterMatcher {
		private StringMatcher fMatcher;

		@Override
		public void setFilter(String pattern, boolean ignoreCase,
				boolean ignoreWildCards) {
			fMatcher = new StringMatcher(pattern + '*', ignoreCase,
					ignoreWildCards);
		}

		@Override
		public boolean match(Object element) {
			return fMatcher.match(fLabelProvider.getText(element));
		}
	}

	private Table fList;

	ILabelProvider fLabelProvider;

	private boolean fMatchEmptyString = true;

	private boolean fIgnoreCase;

	private boolean fAllowDuplicates;

	private String fFilter = ""; //$NON-NLS-1$

	private TwoArrayQuickSorter fSorter;

	Object[] fElements = new Object[0];

	Label[] fLabels;

	Vector fImages = new Vector();

	int[] fFoldedIndices;

	int fFoldedCount;

	int[] fFilteredIndices;

	int fFilteredCount;

	private FilterMatcher fFilterMatcher = new DefaultFilterMatcher();

	Comparator fComparator;

	TableUpdateJob fUpdateJob;

	private static class Label {
		public final String string;

		public final Image image;

		public Label(String newString, Image image) {
			if (newString == null) {
				this.string = Util.ZERO_LENGTH_STRING;
			} else {
				this.string = newString;
			}
			this.image = image;
		}

		public boolean equals(Label label) {
			if (label == null) {
				return false;
			}
			if (string == null && label.string != null) {
				return false;
			}
			if ((string != null) && (!string.equals(label.string))) {
				return false;
			}
			if (image == null) {
				return label.image == null;
			}
			return image.equals(label.image);
		}
	}

	private final class LabelComparator implements Comparator {
		private boolean labelIgnoreCase;

		LabelComparator(boolean ignoreCase) {
			labelIgnoreCase = ignoreCase;
		}

		@Override
		public int compare(Object left, Object right) {
			Label leftLabel = (Label) left;
			Label rightLabel = (Label) right;
			int value;
			if (fComparator == null) {
				value = labelIgnoreCase ? leftLabel.string
						.compareToIgnoreCase(rightLabel.string)
						: leftLabel.string.compareTo(rightLabel.string);
			} else {
				value = fComparator
						.compare(leftLabel.string, rightLabel.string);
			}
			if (value != 0) {
				return value;
			}
			if (leftLabel.image == null) {
				return (rightLabel.image == null) ? 0 : -1;
			} else if (rightLabel.image == null) {
				return +1;
			} else {
				return fImages.indexOf(leftLabel.image)
						- fImages.indexOf(rightLabel.image);
			}
		}
	}

	public FilteredList(Composite parent, int style,
			ILabelProvider labelProvider, boolean ignoreCase,
			boolean allowDuplicates, boolean matchEmptyString) {
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		fList = new Table(this, style);
		fList.setLayoutData(new GridData(GridData.FILL_BOTH));
		fList.setFont(parent.getFont());
		fList.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				fLabelProvider.dispose();
				if (fUpdateJob != null) {
					fUpdateJob.cancel();
				}
			}
		});
		fLabelProvider = labelProvider;
		fIgnoreCase = ignoreCase;
		fSorter = new TwoArrayQuickSorter(new LabelComparator(ignoreCase));
		fAllowDuplicates = allowDuplicates;
		fMatchEmptyString = matchEmptyString;
	}

	public void setElements(Object[] elements) {
		if (elements == null) {
			fElements = new Object[0];
		} else {
			fElements = new Object[elements.length];
			System.arraycopy(elements, 0, fElements, 0, elements.length);
		}
		int length = fElements.length;
		fLabels = new Label[length];
		Set imageSet = new HashSet();
		for (int i = 0; i != length; i++) {
			String text = fLabelProvider.getText(fElements[i]);
			Image image = fLabelProvider.getImage(fElements[i]);
			fLabels[i] = new Label(text, image);
			imageSet.add(image);
		}
		fImages.clear();
		fImages.addAll(imageSet);
		fSorter.sort(fLabels, fElements);
		fFilteredIndices = new int[length];
		fFoldedIndices = new int[length];
		updateList();
	}

	public boolean isEmpty() {
		return (fElements == null) || (fElements.length == 0);
	}

	public void setFilterMatcher(FilterMatcher filterMatcher) {
		Assert.isNotNull(filterMatcher);
		fFilterMatcher = filterMatcher;
	}

	public void setComparator(Comparator comparator) {
		Assert.isNotNull(comparator);
		fComparator = comparator;
	}

	public void addSelectionListener(SelectionListener listener) {
		fList.addSelectionListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		fList.removeSelectionListener(listener);
	}

	public void setSelection(int[] selection) {
		if (selection == null || selection.length == 0) {
			fList.deselectAll();
		} else {
			if (fUpdateJob == null) {
				fList.setSelection(selection);
				fList.notifyListeners(SWT.Selection, new Event());
			} else {
				fUpdateJob.updateSelection(selection);
			}
		}
	}

	public int[] getSelectionIndices() {
		return fList.getSelectionIndices();
	}

	public int getSelectionIndex() {
		return fList.getSelectionIndex();
	}

	public void setSelection(Object[] elements) {
		if (elements == null || elements.length == 0) {
			fList.deselectAll();
			return;
		}
		if (fElements == null) {
			return;
		}
		int[] indices = new int[elements.length];
		for (int i = 0; i != elements.length; i++) {
			int j;
			for (j = 0; j != fFoldedCount; j++) {
				int max = (j == fFoldedCount - 1) ? fFilteredCount
						: fFoldedIndices[j + 1];
				int l;
				for (l = fFoldedIndices[j]; l != max; l++) {
					if (fElements[fFilteredIndices[l]].equals(elements[i])) {
						indices[i] = j;
						break;
					}
				}
				if (l != max) {
					break;
				}
			}
			if (j == fFoldedCount) {
				indices[i] = 0;
			}
		}
		setSelection(indices);
	}

	public Object[] getSelection() {
		if (fList.isDisposed() || (fList.getSelectionCount() == 0)) {
			return new Object[0];
		}
		int[] indices = fList.getSelectionIndices();
		Object[] elements = new Object[indices.length];
		for (int i = 0; i != indices.length; i++) {
			elements[i] = fElements[fFilteredIndices[fFoldedIndices[indices[i]]]];
		}
		return elements;
	}

	public void setFilter(String filter) {
		fFilter = (filter == null) ? "" : filter; //$NON-NLS-1$
		updateList();
	}

	private void updateList() {
		fFilteredCount = filter();
		fFoldedCount = fold();
		if (fUpdateJob != null) {
			fUpdateJob.cancel();
		}
		fUpdateJob = new TableUpdateJob(fList, fFoldedCount);
		fUpdateJob.schedule();
	}

	public String getFilter() {
		return fFilter;
	}

	public Object[] getFoldedElements(int index) {
		if ((index < 0) || (index >= fFoldedCount)) {
			return null;
		}
		int start = fFoldedIndices[index];
		int count = (index == fFoldedCount - 1) ? fFilteredCount - start
				: fFoldedIndices[index + 1] - start;
		Object[] elements = new Object[count];
		for (int i = 0; i != count; i++) {
			elements[i] = fElements[fFilteredIndices[start + i]];
		}
		return elements;
	}

	private int fold() {
		if (fAllowDuplicates) {
			for (int i = 0; i != fFilteredCount; i++) {
				fFoldedIndices[i] = i; // identity mapping
			}
			return fFilteredCount;
		}
		int k = 0;
		Label last = null;
		for (int i = 0; i != fFilteredCount; i++) {
			int j = fFilteredIndices[i];
			Label current = fLabels[j];
			if (!current.equals(last)) {
				fFoldedIndices[k] = i;
				k++;
				last = current;
			}
		}
		return k;
	}

	private int filter() {
		if (((fFilter == null) || (fFilter.length() == 0))
				&& !fMatchEmptyString) {
			return 0;
		}
		fFilterMatcher.setFilter(fFilter.trim(), fIgnoreCase, false);
		int k = 0;
		for (int i = 0; i != fElements.length; i++) {
			if (fFilterMatcher.match(fElements[i])) {
				fFilteredIndices[k++] = i;
			}
		}
		return k;
	}

	private class TableUpdateJob extends WorkbenchJob {
		final Table fTable;

		final int fCount;

		private int currentIndex = 0;

		int[] indicesToSelect;
		
		private boolean readyForSelection = false;

		public TableUpdateJob(Table table, int count) {
			super(WorkbenchMessages.FilteredList_UpdateJobName);
			setSystem(true);
			fTable = table;
			fCount = count;
		}

@Override
public IStatus runInUIThread(IProgressMonitor monitor) {
            if (fTable.isDisposed()) {
				return Status.CANCEL_STATUS;
			}
            int itemCount = fTable.getItemCount();
                        
            if (fCount < itemCount) {
                fTable.setRedraw(false);
                fTable.remove(fCount, itemCount - 1);
                fTable.setRedraw(true);
                itemCount = fTable.getItemCount();
            }
            if (fCount == 0) {
                fTable.notifyListeners(SWT.Selection, new Event());
                return Status.OK_STATUS;
            }
            int iterations = Math.min(10, fCount - currentIndex);
            for (int i = 0; i < iterations; i++) {
                if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
                final TableItem item = (currentIndex < itemCount) ? fTable
                        .getItem(currentIndex)
                        : new TableItem(fTable, SWT.NONE);
                final Label label = fLabels[fFilteredIndices[fFoldedIndices[currentIndex]]];
                item.setText(label.string);
                item.setImage(label.image);
                currentIndex++;
            }
            if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
            if (currentIndex < fCount) {
				schedule(100);
			} else {
                if (indicesToSelect == null) {
                    if (fCount > 0) {
                    	if (fTable.getSelectionIndices().length == 0) {
                    		defaultSelect();
                    	} else {
                    		fTable.notifyListeners(SWT.Selection, new Event());
                    	}
                    }
                } else {
                    selectAndNotify(indicesToSelect);
                }
                readyForSelection = true;
            }
            return Status.OK_STATUS;
        }
		void updateSelection(final int[] indices) {
			indicesToSelect = indices;
			if (readyForSelection) {
				selectAndNotify(indices);
			}
		}

		private void defaultSelect() {
			selectAndNotify(new int[] { 0 });
		}

		private void selectAndNotify(final int[] indices) {
			if (fTable.isDisposed()) {
				return;
			}
			fTable.setSelection(indices);
			fTable.notifyListeners(SWT.Selection, new Event());
		}
	}

	public boolean getAllowDuplicates() {
		return fAllowDuplicates;
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.fAllowDuplicates = allowDuplicates;
	}

	public boolean getIgnoreCase() {
		return fIgnoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.fIgnoreCase = ignoreCase;
	}

	public boolean getMatchEmptyString() {
		return fMatchEmptyString;
	}

	public void setMatchEmptyString(boolean matchEmptyString) {
		this.fMatchEmptyString = matchEmptyString;
	}

	public ILabelProvider getLabelProvider() {
		return fLabelProvider;
	}

	public void setLabelProvider(ILabelProvider labelProvider) {
		this.fLabelProvider = labelProvider;
	}
	
	@Override
	public Accessible getAccessible() {
		return fList.getAccessible();
	}
}
