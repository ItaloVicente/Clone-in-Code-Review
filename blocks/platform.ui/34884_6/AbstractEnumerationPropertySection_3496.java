package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor.HockeyleagueEditor;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEnumerationPropertySection
	extends AbstractHockeyleaguePropertySection {

	protected CCombo combo;

	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		combo = getWidgetFactory().createCCombo(composite); 
		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite,
			new String[] {getLabelText()}));
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		combo.setLayoutData(data);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite,
			getLabelText());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(combo, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(combo, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);

		combo.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				handleComboModified();
			}
		});
	}

	protected void handleComboModified() {

		int index = combo.getSelectionIndex();
		boolean equals = isEqual(index);
		if (!equals) {
			EditingDomain editingDomain = ((HockeyleagueEditor) getPart())
				.getEditingDomain();
			Object value = getFeatureValue(index);
			if (eObjectList.size() == 1) {
				editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain, eObject, getFeature(),
						value));
			} else {
				CompoundCommand compoundCommand = new CompoundCommand();
				for (Iterator i = eObjectList.iterator(); i.hasNext();) {
					EObject nextObject = (EObject) i.next();
					compoundCommand.append(SetCommand.create(editingDomain,
						nextObject, getFeature(), value));
				}
				editingDomain.getCommandStack().execute(compoundCommand);
			}
		}
	}

	public void refresh() {
		combo.setItems(getEnumerationFeatureValues());
		combo.setText(getFeatureAsText());
	}

	protected abstract boolean isEqual(int index);

	protected abstract EAttribute getFeature();

	protected abstract String[] getEnumerationFeatureValues();

	protected abstract String getFeatureAsText();

	protected abstract Object getFeatureValue(int index);

	protected abstract String getLabelText();
}
