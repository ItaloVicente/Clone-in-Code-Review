package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor.HockeyleagueEditor;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractMeasurementPropertySection
	extends AbstractIntegerPropertySection {

	protected Button radioLeft;

	protected Button radioRight;

	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);

		String[] labels = getEnumerationLabels();

		radioLeft = getWidgetFactory().createButton(composite, labels[0],
			SWT.RADIO);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		radioLeft.setLayoutData(data);

		radioRight = getWidgetFactory().createButton(composite, labels[1],
			SWT.RADIO);
		data = new FormData();
		data.left = new FormAttachment(radioLeft,
			ITabbedPropertyConstants.HSPACE);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		radioRight.setLayoutData(data);

		SelectionListener selectionListener = new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				handleRadioModified(((Button) e.getSource()));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

		};

		radioLeft.addSelectionListener(selectionListener);
		radioRight.addSelectionListener(selectionListener);
	}

	protected void handleRadioModified(Button button) {

		int index;
		if (button == radioLeft) {
			index = 0;
		} else {
			index = 1;
		}
		boolean equals = isEnumerationEqual(index);
		if (!equals) {
			EditingDomain editingDomain = ((HockeyleagueEditor) getPart())
				.getEditingDomain();
			Object value = getEnumerationFeatureValue(index);
			if (eObjectList.size() == 1) {
				editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain, eObject,
						getEnumerationFeature(), value));
			} else {
				CompoundCommand compoundCommand = new CompoundCommand();
				for (Iterator i = eObjectList.iterator(); i.hasNext();) {
					EObject nextObject = (EObject) i.next();
					compoundCommand.append(SetCommand.create(editingDomain,
						nextObject, getEnumerationFeature(), value));
				}
				editingDomain.getCommandStack().execute(compoundCommand);
			}
		}
	}

	public void refresh() {
		super.refresh();
		if (getEnumerationIndex() == 0) {
			radioLeft.setSelection(true);
			radioRight.setSelection(false);
		} else {
			radioLeft.setSelection(false);
			radioRight.setSelection(true);
		}

	}

	protected abstract boolean isEnumerationEqual(int index);

	protected abstract EAttribute getEnumerationFeature();

	protected abstract String[] getEnumerationLabels();

	protected abstract int getEnumerationIndex();

	protected abstract Object getEnumerationFeatureValue(int index);
}
