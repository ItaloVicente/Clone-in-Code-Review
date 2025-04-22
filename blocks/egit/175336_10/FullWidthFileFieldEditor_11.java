package org.eclipse.egit.ui.internal.preferences;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

class FullWidthFileFieldEditor extends FileFieldEditor {

	private Composite wrapper;

	private GridData layoutData;

	public FullWidthFileFieldEditor(String configName, String label,
			boolean enforceAbsolute, Composite parent) {
		super(configName, label, enforceAbsolute, parent);
	}

	@Override
	protected void createControl(Composite parent) {
		wrapper = new Composite(parent, SWT.NONE);
		layoutData = GridDataFactory.fillDefaults().grab(true, false)
				.create();
		wrapper.setLayoutData(layoutData);
		GridLayoutFactory.fillDefaults()
				.numColumns(super.getNumberOfControls()).applyTo(wrapper);
		doFillIntoGrid(wrapper, super.getNumberOfControls());
		if (SystemReader.getInstance().isMacOS()) {
			getChangeControl(wrapper).setToolTipText(
					UIText.FullWidthFileFieldEditor_buttonTooltipMac);
		}
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		if (parent != wrapper) {
			layoutData.horizontalSpan = numColumns;
		}
		super.doFillIntoGrid(wrapper, super.getNumberOfControls());
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		layoutData.horizontalSpan = numColumns;
	}

	@Override
	public Label getLabelControl(Composite parent) {
		return super.getLabelControl(wrapper);
	}

	@Override
	public Text getTextControl(Composite parent) {
		return super.getTextControl(wrapper);
	}

	@Override
	protected Button getChangeControl(Composite parent) {
		return super.getChangeControl(wrapper);
	}

	@Override
	public int getNumberOfControls() {
		return 1;
	}
}
