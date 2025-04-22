
package org.eclipse.jface.tests.databinding.preference;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.preference.PreferencePageSupport;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class PreferencePageSupportTest extends AbstractSWTTestCase {
	private PreferencePageWithSupport page;


	@Override
	protected void setUp() throws Exception {
		super.setUp();

		page = new PreferencePageWithSupport();
		page.setControl(getShell());
	}

	public void testCreateAndDestroySupport() {
		page.createContents(getShell());
	}

	public class PreferencePageWithSupport extends PreferencePage {
		@Override
		public void setControl(Control newControl) {
			super.setControl(newControl);
		}

		@Override
		public Control createContents(Composite parent) {
			Composite contents = new Composite(parent, SWT.NONE);

			PreferencePageSupport.create(this, new DataBindingContext());

			return contents;
		}
	}
}
