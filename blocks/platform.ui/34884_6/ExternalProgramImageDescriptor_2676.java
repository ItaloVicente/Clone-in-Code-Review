package org.eclipse.ui.internal.misc;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.Util;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.osgi.framework.Bundle;

public class ExternalEditor {
    private IPath filePath;

    private EditorDescriptor descriptor;

    public ExternalEditor(IPath newFilePath, EditorDescriptor editorDescriptor) {
        this.filePath = newFilePath;
        this.descriptor = editorDescriptor;
    }

    public void open() throws CoreException {

        Program program = this.descriptor.getProgram();
        if (program == null) {
            openWithUserDefinedProgram();
        } else {
            String path = ""; //$NON-NLS-1$
            if (filePath != null) {
                path = filePath.toOSString();
                if (program.execute(path)) {
					return;
				}
            }
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            WorkbenchPlugin.PI_WORKBENCH,
                            0,
                            NLS.bind(WorkbenchMessages.ExternalEditor_errorMessage, path), 
                            null));
        }
    }

    public void openWithUserDefinedProgram() throws CoreException {

        String programFileName = null;
        IConfigurationElement configurationElement = descriptor
                .getConfigurationElement();

        if (configurationElement != null) {
            try {
                Bundle bundle = Platform.getBundle(configurationElement
                        .getNamespace());
                URL entry = bundle.getEntry(descriptor.getFileName());
                if (entry != null) {
                    URL localName = Platform.asLocalURL(entry);
                    File file = new File(localName.getFile());
                    if (file.exists()) {
						programFileName = file.getAbsolutePath();
					}
                }
            } catch (IOException e) {
            }
        }

        if (programFileName == null) {
            programFileName = descriptor.getFileName();
		}

        if (filePath == null) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            WorkbenchPlugin.PI_WORKBENCH,
                            0,
                            NLS.bind(WorkbenchMessages.ExternalEditor_errorMessage,programFileName), 
                            null));
        }
        String path = filePath.toOSString();



        try {
			if (Util.isMac()) {
				Runtime.getRuntime().exec(
						new String[] { "open", "-a", programFileName, path }); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				Runtime.getRuntime().exec(
						new String[] { programFileName, path });
			}
        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            WorkbenchPlugin.PI_WORKBENCH,
                            0,
                            NLS.bind(WorkbenchMessages.ExternalEditor_errorMessage,programFileName),
                            e));
        }
    }
}
