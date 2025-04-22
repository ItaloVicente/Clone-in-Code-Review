package org.eclipse.e4.ui.internal.workbench.swt.dialog.about.ui;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.swt.dialog.InstallationPage;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public abstract class ProductInfoPage extends InstallationPage implements IShellProvider {

	private IProduct product;

	private String productName;

	protected IProduct getProduct() {
		if (product == null)
			product = Platform.getProduct();
		return product;
	}

	public String getProductName() {
		if (productName == null) {
			if (getProduct() != null) {
				productName = getProduct().getName();
			}
			if (productName == null) {
				productName = WorkbenchSWTMessages.AboutDialog_defaultProductName;
			}
		}
		return productName;
	}

	public void setProductName(String name) {
		productName = name;
	}

	abstract String getId();

	protected Composite createOuterComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		return composite;
	}
}
