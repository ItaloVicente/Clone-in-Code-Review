
package org.eclipse.jface.tests.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class SamplePreferencePage extends PreferencePage {
	
	private String text;
	
	public SamplePreferencePage(String title, String text) {
		super(title);
		this.text = text;
	}

	@Override
	protected Control createContents(Composite parent) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        
        return new Composite(parent, SWT.NULL);
	}

}
