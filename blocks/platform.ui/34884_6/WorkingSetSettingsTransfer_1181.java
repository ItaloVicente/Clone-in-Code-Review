package org.eclipse.ui.internal.preferences;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.preferences.WizardPropertyPage;

public class WorkingSetPropertyPage extends WizardPropertyPage {
	
	private static final class ReadOnlyWizard extends Wizard {
		
		public ReadOnlyWizard() {
		}
		
		@Override
		public boolean performFinish() {
			return true;
		}
		
		@Override
		public void addPages() {
			addPage(new ReadOnlyPage());
		}
	}
	
	private static final class ReadOnlyPage extends WizardPage {
		
		protected ReadOnlyPage() {
			super(WorkbenchMessages.WorkingSetPropertyPage_ReadOnlyWorkingSet_title);
			setDescription(WorkbenchMessages.WorkingSetPropertyPage_ReadOnlyWorkingSet_description);
		}
		
		@Override
		public void createControl(Composite parent) {
			Composite composite= new Composite(parent, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			composite.setLayout(new GridLayout(1, false));
			
			setControl(composite);
		}
	}
	
	private IWorkingSet fWorkingSet;
	
	public WorkingSetPropertyPage() {
		noDefaultAndApplyButton();
	}
	
	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		
		if (element instanceof IWorkingSet) {
			fWorkingSet= (IWorkingSet)element;
		} else {
			fWorkingSet= (IWorkingSet)element.getAdapter(IWorkingSet.class);
		}
	}
	
	@Override
	protected void applyChanges() {
	}
	
	@Override
	protected IWizard createWizard() {
		if (fWorkingSet.isEditable()) {
			return PlatformUI.getWorkbench().getWorkingSetManager().createWorkingSetEditWizard(fWorkingSet);
		}
		
		return new ReadOnlyWizard();
	}
	
}
