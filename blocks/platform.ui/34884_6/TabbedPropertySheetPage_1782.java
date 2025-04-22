package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public final class TabContents {

    private ISection[] sections;

    private boolean controlsCreated;

    public TabContents() {
        controlsCreated = false;
    }

    public int getSectionIndex(ISection section) {
        for (int i = 0; i < sections.length; i++) {
			if (section == sections[i]) {
				return i;
			}
		}
        return -1;
    }

    public ISection getSectionAtIndex(int i) {
        if (i >= 0 && i < sections.length) {
			return sections[i];
		}
        return null;
    }

    public ISection[] getSections() {
        return sections;
    }

    public void createControls(Composite parent,
            final TabbedPropertySheetPage page) {
        Composite pageComposite = page.getWidgetFactory().createComposite(
            parent, SWT.NO_FOCUS);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        pageComposite.setLayout(layout);

        for (int i = 0; i < sections.length; i++) {
            final ISection section = sections[i];
            final Composite sectionComposite = page.getWidgetFactory()
                .createComposite(pageComposite, SWT.NO_FOCUS);
            sectionComposite.setLayout(new FillLayout());
            int style = (section.shouldUseExtraSpace()) ? GridData.FILL_BOTH
                : GridData.FILL_HORIZONTAL;
            GridData data = new GridData(style);
            data.heightHint = section.getMinimumHeight();
            sectionComposite.setLayoutData(data);

            ISafeRunnable runnable = new SafeRunnable() {

                public void run()
                    throws Exception {
                    section.createControls(sectionComposite, page);
                }
            };
            SafeRunnable.run(runnable);
        }
        controlsCreated = true;
    }

    public void dispose() {
        for (int i = 0; i < sections.length; i++) {
            final ISection section = sections[i];
            ISafeRunnable runnable = new SafeRunnable() {

                public void run()
                    throws Exception {
                    section.dispose();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    public void aboutToBeShown() {
        for (int i = 0; i < sections.length; i++) {
            final ISection section = sections[i];
            ISafeRunnable runnable = new SafeRunnable() {

                public void run()
                    throws Exception {
                    section.aboutToBeShown();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    public void aboutToBeHidden() {
        for (int i = 0; i < sections.length; i++) {
            final ISection section = sections[i];
            ISafeRunnable runnable = new SafeRunnable() {

                public void run()
                    throws Exception {
                    section.aboutToBeHidden();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    public void setInput(final IWorkbenchPart part, final ISelection selection) {
        for (int i = 0; i < sections.length; i++) {
            final ISection section = sections[i];
            ISafeRunnable runnable = new SafeRunnable() {

                public void run()
                    throws Exception {
                    section.setInput(part, selection);
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    public void setSections(ISection[] sections) {
        this.sections = sections;
    }

    public boolean controlsHaveBeenCreated() {
        return controlsCreated;
    }

    public void refresh() {
        if (controlsCreated) {
            for (int i = 0; i < sections.length; i++) {
                final ISection section = sections[i];
                ISafeRunnable runnable = new SafeRunnable() {

                    public void run()
                        throws Exception {
                        section.refresh();
                    }
                };
                SafeRunnable.run(runnable);
            }
        }
    }
}
