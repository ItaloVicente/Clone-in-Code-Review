
package org.eclipse.ui.tests.statushandlers;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class FaultyExportWizard extends Wizard implements IExportWizard {

	public FaultyExportWizard(String name) {
		super();
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("window title");
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(new MyWizardPage("wizard"));
	}

	public class MyWizardPage extends WizardPage {
		protected MyWizardPage(String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(Composite parent) {
			Composite page = new Composite(parent, SWT.NONE);
			GridLayout pageLayout = new GridLayout();
			page.setLayout(pageLayout);
			page.setLayoutData(new GridData(GridData.FILL_BOTH));
			Label nameLabel = new Label(page, SWT.NONE);
			nameLabel.setText("some label");
			setControl(page);
		}
	}
}
