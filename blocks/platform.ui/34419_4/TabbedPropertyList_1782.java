package org.eclipse.ui.internal.views.properties.tabbed.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


public class TabbedPropertyComposite
	extends Composite {

	private TabbedPropertySheetWidgetFactory factory;

	private Composite mainComposite;

	private Composite leftComposite;

	private ScrolledComposite scrolledComposite;

	private Composite tabComposite;

	private TabbedPropertyTitle title;

	private TabbedPropertyList listComposite;

	private boolean displayTitle;

	public TabbedPropertyComposite(Composite parent,
			TabbedPropertySheetWidgetFactory factory, boolean displayTitle) {
		super(parent, SWT.NO_FOCUS);
		this.factory = factory;
		this.displayTitle = displayTitle;

		createMainComposite();
	}

	protected void createMainComposite() {
		mainComposite = factory.createComposite(this, SWT.NO_FOCUS);
		mainComposite.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(0, 0);
		formData.bottom = new FormAttachment(100, 0);
		mainComposite.setLayoutData(formData);

		createMainContents();
	}

	protected void createMainContents() {
        if (displayTitle) {
            title = new TabbedPropertyTitle(mainComposite, factory);

            FormData data = new FormData();
            data.left = new FormAttachment(0, 0);
            data.right = new FormAttachment(100, 0);
            data.top = new FormAttachment(0, 0);
            title.setLayoutData(data);
        }

		leftComposite = factory.createComposite(mainComposite, SWT.NO_FOCUS);
		leftComposite.setLayout(new FormLayout());

		scrolledComposite = factory.createScrolledComposite(mainComposite, SWT.H_SCROLL
			| SWT.V_SCROLL | SWT.NO_FOCUS);

		FormData formData = new FormData();
		formData.left = new FormAttachment(leftComposite, 0);
		formData.right = new FormAttachment(100, 0);
        if (displayTitle) {
            formData.top = new FormAttachment(title, 0);
        } else {
            formData.top = new FormAttachment(0, 0);
        }
		formData.bottom = new FormAttachment(100, 0);
		scrolledComposite.setLayoutData(formData);

		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(scrolledComposite, 0);
        if (displayTitle) {
            formData.top = new FormAttachment(title, 0);
        } else {
            formData.top = new FormAttachment(0, 0);
        }
		formData.bottom = new FormAttachment(100, 0);
		leftComposite.setLayoutData(formData);

        tabComposite = factory.createComposite(scrolledComposite, SWT.NO_FOCUS);
        tabComposite.setLayout(new FormLayout());

		scrolledComposite.setContent(tabComposite);
		scrolledComposite.setAlwaysShowScrollBars(false);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		listComposite = new TabbedPropertyList(leftComposite, factory);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(0, 0);
		formData.bottom = new FormAttachment(100, 0);
		listComposite.setLayoutData(formData);
	}

	public TabbedPropertyList getList() {
		return listComposite;
	}

	public TabbedPropertyTitle getTitle() {
		return title;
	}

	public Composite getTabComposite() {
		return tabComposite;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}

	protected TabbedPropertySheetWidgetFactory getFactory() {
		return factory;
	}

	public void dispose() {
		listComposite.dispose();
		if (displayTitle) {
			title.dispose();
		}
		super.dispose();
	}
}
