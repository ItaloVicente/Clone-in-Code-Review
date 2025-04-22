/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Helmut J. Haigermoser -  Bug 359838 - The "Workspace Unavailable" error
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 422954
 *     Christian Georgi (SAP) - Bug 423882 - Warn user if workspace is newer than IDE
 *     Andrey Loskutov <loskutov@gmx.de> - Bug 427393, 455162
 *******************************************************************************/
package org.eclipse.ui.internal.ide.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * The "main program" for the Eclipse IDE.
 *
 * @since 3.0
 */
public class IDEApplication implements IApplication, IExecutableExtension {

	/**
	 * The name of the folder containing metadata information for the workspace.
	 */


    private static final Version WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION;
    static {
        Bundle bundle = Platform.getBundle(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
        WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION = bundle != null ? bundle.getVersion() : null/*not installed*/;
    }



    /**
     * A special return code that will be recognized by the launcher and used to
     * restart the workbench.
     */
	private static final Integer EXIT_RELAUNCH = Integer.valueOf(24);

    /**
     * A special return code that will be recognized by the PDE launcher and used to
     * show an error dialog if the workspace is locked.
     */
	private static final Integer EXIT_WORKSPACE_LOCKED = Integer.valueOf(15);

    /**
     * The ID of the application plug-in
     */

    /**
     * Creates a new IDE application.
     */
    public IDEApplication() {
    }

    @Override
	public Object start(IApplicationContext appContext) throws Exception {
        Display display = createDisplay();
        DelayedEventsProcessor processor = new DelayedEventsProcessor(display);

        try {

        	Shell shell = WorkbenchPlugin.getSplashShell(display);
        	if (shell != null) {
        		shell.setText(ChooseWorkspaceDialog.getWindowTitle());
        		shell.setImages(Window.getDefaultImages());
        	}

            Object instanceLocationCheck = checkInstanceLocation(shell, appContext.getArguments());
			if (instanceLocationCheck != null) {
            	WorkbenchPlugin.unsetSplashShell(display);
                return instanceLocationCheck;
            }

            int returnCode = PlatformUI.createAndRunWorkbench(display,
            		new IDEWorkbenchAdvisor(processor));

            if (returnCode != PlatformUI.RETURN_RESTART) {
				return EXIT_OK;
			}

            return EXIT_RELAUNCH.equals(Integer.getInteger(PROP_EXIT_CODE)) ? EXIT_RELAUNCH
                    : EXIT_RESTART;
        } finally {
            if (display != null) {
				display.dispose();
			}
            Location instanceLoc = Platform.getInstanceLocation();
            if (instanceLoc != null)
            	instanceLoc.release();
        }
    }

    /**
     * Creates the display used by the application.
     *
     * @return the display used by the application
     */
    protected Display createDisplay() {
        return PlatformUI.createDisplay();
    }

    @Override
	public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) {
    }

    /**
     * Return <code>null</code> if a valid workspace path has been set and an exit code otherwise.
     * Prompt for and set the path if possible and required.
     *
     * @param applicationArguments the command line arguments
     * @return <code>null</code> if a valid instance location has been set and an exit code
     *         otherwise
     */
    @SuppressWarnings("rawtypes")
	private Object checkInstanceLocation(Shell shell, Map applicationArguments) {
        Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null) {
            MessageDialog
                    .openError(
                            shell,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryTitle,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryMessage);
            return EXIT_OK;
        }

        if (instanceLoc.isSet()) {
            if (!checkValidWorkspace(shell, instanceLoc.getURL())) {
				return EXIT_OK;
			}

            try {
                if (instanceLoc.lock()) {
                    writeWorkspaceVersion();
                    return null;
                }

                File workspaceDirectory = new File(instanceLoc.getURL().getFile());
                if (workspaceDirectory.exists()) {
                	if (isDevLaunchMode(applicationArguments)) {
                		return EXIT_WORKSPACE_LOCKED;
                	}
	                MessageDialog.openError(
	                        shell,
	                        IDEWorkbenchMessages.IDEApplication_workspaceCannotLockTitle,
	                        NLS.bind(IDEWorkbenchMessages.IDEApplication_workspaceCannotLockMessage, workspaceDirectory.getAbsolutePath()));
                } else {
                	MessageDialog.openError(
                			shell,
                			IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
                			IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
                }
            } catch (IOException e) {
                IDEWorkbenchPlugin.log("Could not obtain lock for workspace location", //$NON-NLS-1$
                        e);
                MessageDialog
                .openError(
                        shell,
                        IDEWorkbenchMessages.InternalError,
                        e.getMessage());
            }
            return EXIT_OK;
        }

        ChooseWorkspaceData launchData = new ChooseWorkspaceData(instanceLoc
                .getDefault());

        boolean force = false;

		boolean parentShellVisible = false;
		if (isValidShell(shell)) {
			parentShellVisible = shell.getVisible();
			if (parentShellVisible && launchData.getShowDialog()) {
				shell.setVisible(false);
			}
		}
        while (true) {
            URL workspaceUrl = promptForWorkspace(shell, launchData, force);
            if (workspaceUrl == null) {
				return EXIT_OK;
			}

            force = true;

            try {
                if (instanceLoc.set(workspaceUrl, true)) {
                    launchData.writePersistedData();
                    writeWorkspaceVersion();

					if (parentShellVisible && isValidShell(shell)) {
						shell.setVisible(true);
						shell.forceActive();
					}
                    return null;
                }
            } catch (IllegalStateException e) {
                MessageDialog
                        .openError(
                                shell,
                                IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
                                IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
                return EXIT_OK;
            } catch (IOException e) {
            	  MessageDialog
                  .openError(
                          shell,
                          IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
                          IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
			}

            MessageDialog.openError(shell, IDEWorkbenchMessages.IDEApplication_workspaceInUseTitle,
                    NLS.bind(IDEWorkbenchMessages.IDEApplication_workspaceInUseMessage, workspaceUrl.getFile()));
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean isDevLaunchMode(Map args) {
			return true;
	}

    /**
     * Open a workspace selection dialog on the argument shell, populating the
     * argument data with the user's selection. Perform first level validation
     * on the selection by comparing the version information. This method does
     * not examine the runtime state (e.g., is the workspace already locked?).
     *
     * @param shell
     * @param launchData
     * @param force
     *            setting to true makes the dialog open regardless of the
     *            showDialog value
     * @return An URL storing the selected workspace or null if the user has
     *         canceled the launch operation.
     */
	private URL promptForWorkspace(Shell shell, ChooseWorkspaceData launchData,
			boolean force) {
        URL url = null;

        do {
			new ChooseWorkspaceDialog(shell, launchData, false, true) {
				@Override
				protected Shell getParentShell() {
					return null;
				}

			}.prompt(force);

            String instancePath = launchData.getSelection();
            if (instancePath == null) {
				return null;
			}

            force = true;

            if (instancePath.length() <= 0) {
                MessageDialog
                .openError(
                        shell,
                        IDEWorkbenchMessages.IDEApplication_workspaceEmptyTitle,
                        IDEWorkbenchMessages.IDEApplication_workspaceEmptyMessage);
                continue;
            }

            File workspace = new File(instancePath);
            if (!workspace.exists()) {
				workspace.mkdir();
			}

            try {
                String path = workspace.getAbsolutePath().replace(
                        File.separatorChar, '/');
                url = new URL("file", null, path); //$NON-NLS-1$
            } catch (MalformedURLException e) {
                MessageDialog
                        .openError(
                                shell,
                                IDEWorkbenchMessages.IDEApplication_workspaceInvalidTitle,
                                IDEWorkbenchMessages.IDEApplication_workspaceInvalidMessage);
                continue;
            }
        } while (!checkValidWorkspace(shell, url));

        return url;
    }

	/**
	 * @return true if the shell is not <code>null</code> and not disposed
	 */
	static boolean isValidShell(Shell shell) {
		return shell != null && !shell.isDisposed();
	}

    /**
     * Return true if the argument directory is ok to use as a workspace and
     * false otherwise. A version check will be performed, and a confirmation
     * box may be displayed on the argument shell if an older version is
     * detected.
     *
     * @return true if the argument URL is ok to use as a workspace and false
     *         otherwise.
     */
    private boolean checkValidWorkspace(Shell shell, URL url) {
        if (url == null) {
			return false;
		}

        if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
            return true;
        }

        Version version = readWorkspaceVersion(url);
        if (version == null) {
			return true;
		}

        final Version ide_version = toMajorMinorVersion(WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION);
        Version workspace_version = toMajorMinorVersion(version);
        int versionCompareResult = workspace_version.compareTo(ide_version);

		if (versionCompareResult == 0) {
			return true;
		}

		int severity;
		String title;
		String message;
		if (versionCompareResult < 0) {
			severity = MessageDialog.INFORMATION;
			title = IDEWorkbenchMessages.IDEApplication_versionTitle_olderWorkspace;
			message = NLS.bind(IDEWorkbenchMessages.IDEApplication_versionMessage_olderWorkspace, url.getFile());
		} else {
			severity = MessageDialog.WARNING;
			title = IDEWorkbenchMessages.IDEApplication_versionTitle_newerWorkspace;
			message = NLS.bind(IDEWorkbenchMessages.IDEApplication_versionMessage_newerWorkspace, url.getFile());
		}

		IPersistentPreferenceStore prefStore = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, IDEWorkbenchPlugin.IDE_WORKBENCH);
		boolean keepOnWarning = prefStore.getBoolean(IDEInternalPreferences.WARN_ABOUT_WORKSPACE_INCOMPATIBILITY);
		if (keepOnWarning) {
			MessageDialogWithToggle dialog = new MessageDialogWithToggle(shell, title, null, message, severity,
					new String[] {IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL}, 0,
					IDEWorkbenchMessages.IDEApplication_version_doNotWarnAgain, false);
			if (dialog.open() != Window.OK) {
				return false;
			}
			keepOnWarning = !dialog.getToggleState();
			try {
				prefStore.setValue(IDEInternalPreferences.WARN_ABOUT_WORKSPACE_INCOMPATIBILITY, keepOnWarning);
				prefStore.save();
			} catch (IOException e) {
				IDEWorkbenchPlugin.log("Error writing to configuration preferences", //$NON-NLS-1$
					new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, e.getMessage(), e));
			}
		}
		return true;
	}

    /**
     * Look at the argument URL for the workspace's version information. Return
     * that version if found and null otherwise.
     */
    private static Version readWorkspaceVersion(URL workspace) {
        File versionFile = getVersionFile(workspace, false);
        if (versionFile == null || !versionFile.exists()) {
			return null;
		}

        try {
            Properties props = new Properties();
            FileInputStream is = new FileInputStream(versionFile);
            try {
                props.load(is);
            } finally {
                is.close();
            }

            String versionString = props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }
            versionString= props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }
            return null;
        } catch (IOException e) {
            IDEWorkbenchPlugin.log("Could not read version file " + versionFile, new Status( //$NON-NLS-1$
                    IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
                    IStatus.ERROR,
                    e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
                    e));
            return null;
        } catch (IllegalArgumentException e) {
            IDEWorkbenchPlugin.log("Could not parse version in " + versionFile, new Status( //$NON-NLS-1$
                    IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
                    IStatus.ERROR,
                    e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
                    e));
            return null;
        }
    }

    /**
     * Write the version of the metadata into a known file overwriting any
     * existing file contents. Writing the version file isn't really crucial,
     * so the function is silent about failure
     */
    private static void writeWorkspaceVersion() {
        if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
            return;
        }

        Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null || instanceLoc.isReadOnly()) {
			return;
		}

        File versionFile = getVersionFile(instanceLoc.getURL(), true);
        if (versionFile == null) {
			return;
		}

        OutputStream output = null;
        try {
            output = new FileOutputStream(versionFile);
            Properties props = new Properties();

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME, WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED);

            props.store(output, null);
        } catch (IOException e) {
            IDEWorkbenchPlugin.log("Could not write version file", //$NON-NLS-1$
                    StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
        } finally {
            try {
                if (output != null) {
					output.close();
				}
            } catch (IOException e) {
            }
        }
    }

    /**
     * The version file is stored in the metadata area of the workspace. This
     * method returns an URL to the file or null if the directory or file does
     * not exist (and the create parameter is false).
     *
     * @param create
     *            If the directory and file does not exist this parameter
     *            controls whether it will be created.
     * @return An url to the file or null if the version file does not exist or
     *         could not be created.
     */
    private static File getVersionFile(URL workspaceUrl, boolean create) {
        if (workspaceUrl == null) {
			return null;
		}

        try {
            File metaDir = new File(workspaceUrl.getPath(), METADATA_FOLDER);
            if (!metaDir.exists() && (!create || !metaDir.mkdir())) {
				return null;
			}

            File versionFile = new File(metaDir, VERSION_FILENAME);
            if (!versionFile.exists()
                    && (!create || !versionFile.createNewFile())) {
				return null;
			}

            return versionFile;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return the major and minor parts of the given version
     */
    private static Version toMajorMinorVersion(Version version) {
        return new Version(version.getMajor(), version.getMinor(), 0);
    }

	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
