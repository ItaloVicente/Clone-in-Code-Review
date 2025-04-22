package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor.HockeyleagueEditor;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.TextChangeHelper;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractTextPropertySection
	extends AbstractHockeyleaguePropertySection {

	protected Text text;

	protected TextChangeHelper listener;

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		text = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite,
			new String[] {getLabelText()}));
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		text.setLayoutData(data);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite,
			getLabelText());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(text, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(text, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);

		listener = new TextChangeHelper() {

			public void textChanged(Control control) {
				handleTextModified();
			}
		};
		listener.startListeningTo(text);
		listener.startListeningForEnter(text);
	}

	protected void handleTextModified() {
		String newText = text.getText();
		boolean equals = isEqual(newText);
		if (!equals) {
			EditingDomain editingDomain = ((HockeyleagueEditor) getPart())
				.getEditingDomain();
			Object value = getFeatureValue(newText);
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
		text.setText(getFeatureAsText());
	}

	protected abstract boolean isEqual(String newText);

	protected abstract EAttribute getFeature();

	protected abstract String getFeatureAsText();

	protected abstract Object getFeatureValue(String newText);

	protected abstract String getLabelText();
}
