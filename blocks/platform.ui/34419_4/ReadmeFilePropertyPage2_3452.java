package org.eclipse.ui.examples.readmetool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

public class ReadmeFilePropertyPage extends PropertyPage {

    protected Composite createComposite(Composite parent, int numColumns) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        composite.setLayout(layout);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);
        return composite;
    }

    @Override
	public Control createContents(Composite parent) {

        noDefaultAndApplyButton();
        Composite panel = createComposite(parent, 2);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IReadmeConstants.PROPERTY_PAGE_CONTEXT);


        IResource resource = (IResource) getElement();
        IStatus result = null;
        if (resource.getType() == IResource.FILE) {
            Label label = createLabel(panel, MessageUtil.getString("File_name")); //$NON-NLS-1$
            label = createLabel(panel, resource.getName());
            grabExcessSpace(label);

            createLabel(panel, MessageUtil.getString("Path")); //$NON-NLS-1$
            label = createLabel(panel, resource.getFullPath().setDevice(null)
                    .toString());
            grabExcessSpace(label);

            createLabel(panel, MessageUtil.getString("Size")); //$NON-NLS-1$
            InputStream contentStream = null;
            try {
                IFile file = (IFile) resource;
                contentStream = file.getContents();
                Reader in = new InputStreamReader(contentStream);
                int chunkSize = contentStream.available();
                StringBuffer buffer = new StringBuffer(chunkSize);
                char[] readBuffer = new char[chunkSize];
                int n = in.read(readBuffer);

                while (n > 0) {
                    buffer.append(readBuffer);
                    n = in.read(readBuffer);
                }

                contentStream.close();
                label = createLabel(panel, Integer.toString(buffer.length()));
            } catch (CoreException e) {
                result = e.getStatus();
                String message = result.getMessage();
                if (message == null)
                    label = createLabel(panel, MessageUtil.getString("<Unknown>")); //$NON-NLS-1$
                else
                    label = createLabel(panel, message);
            } catch (IOException e) {
                label = createLabel(panel, MessageUtil.getString("<Unknown>")); //$NON-NLS-1$
            } finally {
                if (contentStream != null) {
                    try {
                        contentStream.close();
                    } catch (IOException e) {
                    }
                }
            }
            grabExcessSpace(label);
            createLabel(panel, MessageUtil.getString("Number_of_sections")); //$NON-NLS-1$
            IAdaptable sections = getSections(resource);
            if (sections instanceof AdaptableList) {
                AdaptableList list = (AdaptableList) sections;
                label = createLabel(panel, String.valueOf(list.size()));
                grabExcessSpace(label);
            }
        }

        Label label = createLabel(panel, MessageUtil
                .getString("Additional_information")); //$NON-NLS-1$
        grabExcessSpace(label);
        GridData gd = (GridData) label.getLayoutData();
        gd.horizontalSpan = 2;
        return new Canvas(panel, 0);
    }

    protected Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    private AdaptableList getSections(IAdaptable adaptable) {
        if (adaptable instanceof IFile)
            return ReadmeModelFactory.getInstance().getSections(
                    (IFile) adaptable);
        return null;
    }

    private void grabExcessSpace(Control control) {
        GridData gd = (GridData) control.getLayoutData();
        if (gd != null) {
            gd.grabExcessHorizontalSpace = true;
        }
    }

    @Override
	public boolean performOk() {
        return true;
    }
}
