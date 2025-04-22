
package org.eclipse.ui.views.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.views.ViewsPlugin;

public class PropertySheetEntry extends EventManager implements
		IPropertySheetEntry {

	private Object[] values = new Object[0];

	private Map sources = new HashMap(0);

	private Object editValue;

	private PropertySheetEntry parent;

	private IPropertySourceProvider propertySourceProvider;

	private IPropertyDescriptor descriptor;

	private CellEditor editor;

	private String errorText;

	private PropertySheetEntry[] childEntries = null;

	private ICellEditorListener cellEditorListener = new ICellEditorListener() {
		@Override
		public void editorValueChanged(boolean oldValidState,
				boolean newValidState) {
			if (!newValidState) {
				setErrorText(editor.getErrorMessage());
			} else {
				setErrorText(null);
			}
		}

		@Override
		public void cancelEditor() {
			setErrorText(null);
		}

		@Override
		public void applyEditorValue() {
			PropertySheetEntry.this.applyEditorValue();
		}
	};

	@Override
	public void addPropertySheetEntryListener(
			IPropertySheetEntryListener listener) {
		addListenerObject(listener);
	}

	@Override
	public void applyEditorValue() {
		if (editor == null) {
			return;
		}

		if (!editor.isValueValid()) {
			setErrorText(editor.getErrorMessage());
			return;
		}

		setErrorText(null);

		Object newValue = editor.getValue();
		boolean changed = false;
		if (values.length > 1) {
			changed = true;
		} else if (editValue == null) {
			if (newValue != null) {
				changed = true;
			}
		} else if (!editValue.equals(newValue)) {
			changed = true;
		}

		if (changed) {
			setValue(newValue);
		}
	}

	private List computeMergedPropertyDescriptors() {
		if (values.length == 0) {
			return new ArrayList(0);
		}

		IPropertySource firstSource = getPropertySource(values[0]);
		if (firstSource == null) {
			return new ArrayList(0);
		}

		if (values.length == 1) {
			return Arrays.asList(firstSource.getPropertyDescriptors());
		}

		Map[] propertyDescriptorMaps = new Map[values.length];
		for (int i = 0; i < values.length; i++) {
			Object object = values[i];
			IPropertySource source = getPropertySource(object);
			if (source == null) {
				return new ArrayList(0);
			}
			propertyDescriptorMaps[i] = computePropertyDescriptorsFor(source);
		}

		Map intersection = propertyDescriptorMaps[0];
		for (int i = 1; i < propertyDescriptorMaps.length; i++) {
			Object[] ids = intersection.keySet().toArray();
			for (int j = 0; j < ids.length; j++) {
				Object object = propertyDescriptorMaps[i].get(ids[j]);
				if (object == null ||
						!((IPropertyDescriptor) intersection.get(ids[j]))
								.isCompatibleWith((IPropertyDescriptor) object)) {
					intersection.remove(ids[j]);
				}
			}
		}

		ArrayList result = new ArrayList(intersection.size());
		IPropertyDescriptor[] firstDescs = firstSource.getPropertyDescriptors();
		for (int i = 0; i < firstDescs.length; i++) {
			IPropertyDescriptor desc = firstDescs[i];
			if (intersection.containsKey(desc.getId())) {
				result.add(desc);
			}
		}
		return result;
	}

	private Map computePropertyDescriptorsFor(IPropertySource source) {
		IPropertyDescriptor[] descriptors = source.getPropertyDescriptors();
		Map result = new HashMap(descriptors.length * 2 + 1);
		for (int i = 0; i < descriptors.length; i++) {
			result.put(descriptors[i].getId(), descriptors[i]);
		}
		return result;
	}

	private void createChildEntries() {
		List descriptors = computeMergedPropertyDescriptors();

		PropertySheetEntry[] newEntries = new PropertySheetEntry[descriptors
				.size()];
		for (int i = 0; i < descriptors.size(); i++) {
			IPropertyDescriptor d = (IPropertyDescriptor) descriptors.get(i);
			PropertySheetEntry entry = createChildEntry();
			entry.setDescriptor(d);
			entry.setParent(this);
			entry.setPropertySourceProvider(propertySourceProvider);
			entry.refreshValues();
			newEntries[i] = entry;
		}
		childEntries = newEntries;
	}

	protected PropertySheetEntry createChildEntry() {
		return new PropertySheetEntry();
	}

	@Override
	public void dispose() {
		if (editor != null) {
			editor.dispose();
			editor = null;
		}
		PropertySheetEntry[] entriesToDispose = childEntries;
		childEntries = null;
		if (entriesToDispose != null) {
			for (int i = 0; i < entriesToDispose.length; i++) {
				if (entriesToDispose[i] != null) {
					entriesToDispose[i].dispose();
				}
			}
		}
	}

	private void fireChildEntriesChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.childEntriesChanged(this);
		}
	}

	private void fireErrorMessageChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.errorMessageChanged(this);
		}
	}

	private void fireValueChanged() {
		Object[] array = getListeners();
		for (int i = 0; i < array.length; i++) {
			IPropertySheetEntryListener listener = (IPropertySheetEntryListener) array[i];
			listener.valueChanged(this);
		}
	}

	@Override
	public String getCategory() {
		return descriptor.getCategory();
	}

	@Override
	public IPropertySheetEntry[] getChildEntries() {
		if (childEntries == null) {
			createChildEntries();
		}
		return childEntries;
	}

	@Override
	public String getDescription() {
		return descriptor.getDescription();
	}

	protected IPropertyDescriptor getDescriptor() {
		return descriptor;
	}

	@Override
	public String getDisplayName() {
		return descriptor.getDisplayName();
	}

	@Override
	public CellEditor getEditor(Composite parent) {

		if (editor == null) {
			editor = descriptor.createPropertyEditor(parent);
			if (editor != null) {
				editor.addListener(cellEditorListener);
			}
		}
		if (editor != null) {
			editor.setValue(editValue);
			setErrorText(editor.getErrorMessage());
		}
		return editor;
	}

	protected Object getEditValue(int index) {
		Object value = values[index];
		IPropertySource source = getPropertySource(value);
		if (source != null) {
			value = source.getEditableValue();
		}
		return value;
	}

	@Override
	public String getErrorText() {
		return errorText;
	}

	@Override
	public String getFilters()[] {
		return descriptor.getFilterFlags();
	}

	@Override
	public Object getHelpContextIds() {
		return descriptor.getHelpContextIds();
	}

	@Override
	public Image getImage() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider == null) {
			return null;
		}
		return provider.getImage(editValue);
	}

	protected PropertySheetEntry getParent() {
		return parent;
	}

	protected IPropertySource getPropertySource(Object object) {
		if (sources.containsKey(object))
			return (IPropertySource) sources.get(object);

		IPropertySource result = null;
		IPropertySourceProvider provider = propertySourceProvider;

		if (provider == null && object != null) {
			provider = (IPropertySourceProvider) ViewsPlugin.getAdapter(object, 
                    IPropertySourceProvider.class, false);
        }

		if (provider != null) {
			result = provider.getPropertySource(object);
		} else {
            result = (IPropertySource)ViewsPlugin.getAdapter(object, IPropertySource.class, false);
        }

		sources.put(object, result);
		return result;
	}

	@Override
	public String getValueAsString() {
		if (editValue == null) {
			return "";//$NON-NLS-1$
		}
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider == null) {
			return editValue.toString();
		}
		String text = provider.getText(editValue);
		if (text == null) {
			return "";//$NON-NLS-1$
		}
		return text;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public boolean hasChildEntries() {
		if (childEntries != null && childEntries.length > 0) {
			return true;
		}
		return computeMergedPropertyDescriptors().size() > 0;
	}

	private void refreshChildEntries() {
		if (childEntries == null) {
			return;
		}

		List descriptors = computeMergedPropertyDescriptors();

		Map entryCache = new HashMap(childEntries.length * 2 + 1);
		for (int i = 0; i < childEntries.length; i++) {
			PropertySheetEntry childEntry = childEntries[i];
			if (childEntry != null) {
				entryCache.put(childEntry.getDescriptor().getId(), childEntry);
			}
		}

		List entriesToDispose = new ArrayList(Arrays.asList(childEntries));

		this.childEntries = null;

		PropertySheetEntry[] newEntries = new PropertySheetEntry[descriptors
				.size()];
		boolean entriesChanged = descriptors.size() != entryCache.size();
		for (int i = 0; i < descriptors.size(); i++) {
			IPropertyDescriptor d = (IPropertyDescriptor) descriptors.get(i);
			PropertySheetEntry entry = (PropertySheetEntry) entryCache.get(d
					.getId());
			if (entry != null) {
				entry.setDescriptor(d);
				entriesToDispose.remove(entry);
			} else {
				entry = createChildEntry();
				entry.setDescriptor(d);
				entry.setParent(this);
				entry.setPropertySourceProvider(propertySourceProvider);
				entriesChanged = true;
			}
			entry.refreshValues();
			newEntries[i] = entry;
		}

		this.childEntries = newEntries;

		if (entriesChanged) {
			fireChildEntriesChanged();
		}

		for (int i = 0; i < entriesToDispose.size(); i++) {
			((IPropertySheetEntry) entriesToDispose.get(i)).dispose();
		}
	}

	protected void refreshFromRoot() {
		if (parent == null) {
			refreshChildEntries();
		} else {
			parent.refreshFromRoot();
		}
	}

	private void refreshValues() {
		Object[] currentSources = parent.getValues();

		Object[] newValues = new Object[currentSources.length];
		for (int i = 0; i < currentSources.length; i++) {
			IPropertySource source = parent
					.getPropertySource(currentSources[i]);
			newValues[i] = source.getPropertyValue(descriptor.getId());
		}

		setValues(newValues);
	}

	@Override
	public void removePropertySheetEntryListener(
			IPropertySheetEntryListener listener) {
		removeListenerObject(listener);
	}

	@Override
	public void resetPropertyValue() {
		if (parent == null) {
			return;
		}

		boolean change = false;
		Object[] objects = parent.getValues();
		for (int i = 0; i < objects.length; i++) {
			IPropertySource source = getPropertySource(objects[i]);
			if (source.isPropertySet(descriptor.getId())) {
				if (source instanceof IPropertySource2) {
					IPropertySource2 extendedSource = (IPropertySource2) source;
					if (!extendedSource
							.isPropertyResettable(descriptor.getId())) {
						continue;
					}
				}
				source.resetPropertyValue(descriptor.getId());
				change = true;
			}
		}
		if (change) {
			refreshFromRoot();
		}
	}

	private void setDescriptor(IPropertyDescriptor newDescriptor) {
		if (descriptor != newDescriptor && editor != null) {
			editor.dispose();
			editor = null;
		}
		descriptor = newDescriptor;
	}

	private void setErrorText(String newErrorText) {
		errorText = newErrorText;
		fireErrorMessageChanged();
	}

	private void setParent(PropertySheetEntry propertySheetEntry) {
		parent = propertySheetEntry;
	}

	public void setPropertySourceProvider(IPropertySourceProvider provider) {
		propertySourceProvider = provider;
	}

	private void setValue(Object newValue) {
		for (int i = 0; i < values.length; i++) {
			values[i] = newValue;
		}

		parent.valueChanged(this);

		refreshFromRoot();
	}

	@Override
	public void setValues(Object[] objects) {
		values = objects;
		sources = new HashMap(values.length * 2 + 1);

		if (values.length == 0) {
			editValue = null;
		} else {
			Object newValue = values[0];

			IPropertySource source = getPropertySource(newValue);
			if (source != null) {
				newValue = source.getEditableValue();
			}
			editValue = newValue;
		}

		refreshChildEntries();

		fireValueChanged();
	}

	protected void valueChanged(PropertySheetEntry child) {
		for (int i = 0; i < values.length; i++) {
			IPropertySource source = getPropertySource(values[i]);
			source.setPropertyValue(child.getDescriptor().getId(), child
					.getEditValue(i));
		}

		if (parent != null) {
			parent.valueChanged(this);
		}
	}

	protected Color getForeground() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getForeground(this);
		}
		return null;
	}

	protected Color getBackground() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getBackground(this);
		}
		return null;
	}

	protected Font getFont() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IFontProvider) {
			return ((IFontProvider) provider).getFont(this);
		}
		return null;
	}
}
