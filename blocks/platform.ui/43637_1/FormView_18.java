package org.eclipse.ui.forms.examples.internal.rcp;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.widgets.*;

public class TypeTwoDetailsPage implements IDetailsPage {
	private IManagedForm mform;
	private TypeTwo input;
	private Button flag1;
	private Button flag2;

	public TypeTwoDetailsPage() {
	}
	public void initialize(IManagedForm mform) {
		this.mform = mform;
	}
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		parent.setLayout(layout);

		FormToolkit toolkit = mform.getToolkit();
		Section s1 = toolkit.createSection(parent, Section.DESCRIPTION);
		s1.marginWidth = 10;
		s1.setText("Type Two Details");
		s1.setDescription("Set the properties of the selected TypeTwo object.");
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);
		toolkit.createCompositeSeparator(s1);
		Composite client = toolkit.createComposite(s1);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		client.setLayout(glayout);

		flag1 = toolkit.createButton(client, "Value of the flag1 property", SWT.CHECK);
		flag1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (input!=null)
					input.setFlag1(flag1.getSelection());
			}
		});

		flag2 = toolkit.createButton(client, "Value of the flag2 property", SWT.CHECK);
		flag2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (input!=null)
					input.setFlag2(flag2.getSelection());
			}
		});
		s1.setClient(client);
	}
	private void update() {
		flag1.setSelection(input!=null && input.getFlag1());
		flag2.setSelection(input!=null && input.getFlag2());
	}
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size()==1) {
			input = (TypeTwo)ssel.getFirstElement();
		}
		else
			input = null;
		update();
	}
	public void commit(boolean onSave) {
	}
	public void setFocus() {
		flag1.setFocus();
	}
	public void dispose() {
	}
	public boolean isDirty() {
		return false;
	}

	public boolean isStale() {
		return false;
	}
	public void refresh() {
		update();
	}
	public boolean setFormInput(Object input) {
		return false;
	}
}
