/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Eric Rizzo - added API to set the list of recent workspaces.
 *     Jan-Ove Weichel <ovi.weichel@gmail.com> - Bug 463039
 *     Jan-Ove Weichel <janove.weichel@vogella.com> - Bug 411578
 *******************************************************************************/
package org.eclipse.ui.internal.ide;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * This class stores the information behind the "Launch Workspace" dialog. The
 * class is able to read and write itself to a well known configuration file.
 */
public class ChooseWorkspaceData {
    /**
     * The default max length of the recent workspace mru list.
     */
	private static final int RECENT_MAX_LENGTH = 10;

    /**
     * The directory within the config area that will be used for the
     * receiver's persisted data.
     */

    /**
     * The name of the file within the config area that will be used for
     * the recever's persisted data.
     * @see PERS_FOLDER
     */

    /**
     * In the past a file was used to store persist these values.  This file was written
     * with this value as its protocol identifier.
     */
    private static final int PERS_ENCODING_VERSION = 1;

    /**
	 * This is the first version of the encode/decode protocol that uses the
	 * configuration area preference store for persistence. The only encoding
	 * done is to convert the recent workspace list into a comma-separated list.
	 */
    private static final int PERS_ENCODING_VERSION_CONFIG_PREFS = 2;

    /**
	 * This is the second version of the encode/decode protocol that uses the
	 * configuration area preferences store for persistence. This version is the
	 * same as the previous version except it uses a \n character to separate
	 * the path entries instead of commas. (see bug 98467)
	 *
	 * @since 3.3.1
	 */
	private static final int PERS_ENCODING_VERSION_CONFIG_PREFS_NO_COMMAS = 3;

    private boolean showDialog = true;

	private boolean showRecentWorkspaces;

    private String initialDefault;

    private String selection;

    private String[] recentWorkspaces;

    private static interface XML {







    }

    /**
     * Creates a new instance, loading persistent data if its found.
     */
    public ChooseWorkspaceData(String initialDefault) {
        readPersistedData();
        setInitialDefault(initialDefault);
    }

    /**
     * Creates a new instance, loading persistent data if its found.
     */
    public ChooseWorkspaceData(URL instanceUrl) {
        readPersistedData();
        if (instanceUrl != null) {
			setInitialDefault(new File(instanceUrl.getFile()).toString());
		}
    }

    /**
     * Return the folder to be used as a default if no other information
     * exists. Does not return null.
     */
    public String getInitialDefault() {
        if (initialDefault == null) {
		}
        return initialDefault;
    }

    /**
     * Set this data's initialDefault parameter to a properly formatted version
     * of the argument directory string. The proper format is to the platform
     * appropriate separator character without meaningless leading or trailing
     * separator characters.
     */
    private void setInitialDefault(String dir) {
        if (dir == null || dir.length() <= 0) {
            initialDefault = null;
            return;
        }

        dir = new Path(dir).toOSString();
        while (dir.charAt(dir.length() - 1) == File.separatorChar) {
			dir = dir.substring(0, dir.length() - 1);
		}
        initialDefault = dir;
    }

    /**
     * Return the currently selected workspace or null if nothing is selected.
     */
    public String getSelection() {
        return selection;
    }

    /**
     * Return the currently selected workspace or null if nothing is selected.
     */
    public boolean getShowDialog() {
        return showDialog;
    }

	/**
	 * Returns whether the "Recent Workspaces" should be shown
	 */
	public boolean isShowRecentWorkspaces() {
		return showRecentWorkspaces;
	}

    /**
     * Return an array of recent workspaces sorted with the most recently used at
     * the start.
     */
    public String[] getRecentWorkspaces() {
        return recentWorkspaces;
    }

    /**
     * The argument workspace has been selected, update the receiver.  Does not
     * persist the new values.
     */
    public void workspaceSelected(String dir) {
        selection = dir;
    }

    /**
     * Toggle value of the showDialog persistent setting.
     */
    public void toggleShowDialog() {
        showDialog = !showDialog;
    }

	/**
	 * Set if the "Recent Workspaces" should be shown
	 */
	public void setShowRecentWorkspaces(boolean showRecentWorkspaces) {
		this.showRecentWorkspaces = showRecentWorkspaces;
	}

    /**
     * Sets the list of recent workspaces.
     */
    public void setRecentWorkspaces(String[] workspaces) {
    	if (workspaces == null) {
    		recentWorkspaces = new String[0];
    	} else {
			recentWorkspaces = workspaces;
		}
    }

    /**
	 * Update the persistent store. Call this function after the currently
	 * selected value has been found to be ok.
	 */
	public void writePersistedData() {
		Preferences node = ConfigurationScope.INSTANCE.getNode(IDEWorkbenchPlugin.IDE_WORKBENCH);

		node.putBoolean(
				IDE.Preferences.SHOW_WORKSPACE_SELECTION_DIALOG,
				showDialog);

		node.putInt(IDE.Preferences.MAX_RECENT_WORKSPACES,
				recentWorkspaces.length);

		if (selection != null) {
			File newFolder = new File(selection);
			String oldEntry = recentWorkspaces[0];
			recentWorkspaces[0] = selection;
			for (int i = 1; i < recentWorkspaces.length && oldEntry != null; ++i) {
				File oldFolder = new File (oldEntry);
				if (newFolder.compareTo(oldFolder) == 0){
					break;
				}
				String tmp = recentWorkspaces[i];
				recentWorkspaces[i] = oldEntry;
				oldEntry = tmp;
			}
		}

		String encodedRecentWorkspaces = encodeStoredWorkspacePaths(recentWorkspaces);
		node.put(IDE.Preferences.RECENT_WORKSPACES,
				encodedRecentWorkspaces);

		node.putInt(IDE.Preferences.RECENT_WORKSPACES_PROTOCOL,
				PERS_ENCODING_VERSION_CONFIG_PREFS_NO_COMMAS);

		node.putBoolean(IDE.Preferences.SHOW_RECENT_WORKSPACES, showRecentWorkspaces);

		try {
			node.flush();
		} catch (BackingStoreException e) {
		}
	}

    /**
	 * Look for and read data that might have been persisted from some previous
	 * run. Leave the receiver in a default state if no persistent data is
	 * found.
	 *
	 * @return true if a file was successfully read and false otherwise
	 */
    private boolean readPersistedData_file() {
	    URL persUrl = null;

	    Location configLoc = Platform.getConfigurationLocation();
	    if (configLoc != null) {
			persUrl = getPersistenceUrl(configLoc.getURL(), false);
		}

	    try {
	        if (persUrl == null) {
				return false;
			}


	        Reader reader = new FileReader(persUrl.getFile());
	        XMLMemento memento = XMLMemento.createReadRoot(reader);
	        if (memento == null || !compatibleFileProtocol(memento)) {
				return false;
			}

	        IMemento alwaysAskTag = memento.getChild(XML.ALWAYS_ASK);
	        showDialog = alwaysAskTag == null ? true : alwaysAskTag.getInteger(
	                XML.SHOW_DIALOG).intValue() == 1;

	        IMemento recent = memento.getChild(XML.RECENT_WORKSPACES);
	        if (recent == null) {
				return false;
			}

	        Integer maxLength = recent.getInteger(XML.MAX_LENGTH);
	        int max = RECENT_MAX_LENGTH;
	        if (maxLength != null) {
				max = maxLength.intValue();
			}

	        IMemento indices[] = recent.getChildren(XML.WORKSPACE);
	        if (indices == null || indices.length <= 0) {
				return false;
			}

	        max = Math.max(max, indices.length);

	        recentWorkspaces = new String[max];
	        for (int i = 0; i < indices.length; ++i) {
	            String path = indices[i].getString(XML.PATH);
	            if (path == null) {
					break;
				}
	            recentWorkspaces[i] = path;
	        }
	    } catch (IOException e) {
	        return false;
	    } catch (WorkbenchException e) {
	        return false;
	    } finally {
	        if (recentWorkspaces == null) {
				recentWorkspaces = new String[RECENT_MAX_LENGTH];
			}
	    }

	    return true;
	}

    /**
     * Return the current (persisted) value of the "showDialog on startup"
     * preference. Return the global default if the file cannot be accessed.
     */
    public static boolean getShowDialogValue() {


        return data.readPersistedData() ? data.showDialog : true;
    }

    /**
	 * Return the current (persisted) value of the "showDialog on startup"
	 * preference. Return the global default if the file cannot be accessed.
	 */
	public static void setShowDialogValue(boolean showDialog) {


		data.showDialog = showDialog;
		data.writePersistedData();
	}

    /**
	 * Look in the config area preference store for the list of recently used
	 * workspaces.
	 *
	 * NOTE: During the transition phase the file will be checked if no config
	 * preferences are found.
	 *
	 * @return true if the values were successfully retrieved and false
	 *         otherwise
	 */
	public boolean readPersistedData() {
		IPreferenceStore store = new ScopedPreferenceStore(ConfigurationScope.INSTANCE,
				IDEWorkbenchPlugin.IDE_WORKBENCH);



		int protocol = store.getInt(IDE.Preferences.RECENT_WORKSPACES_PROTOCOL);
		if (protocol == IPreferenceStore.INT_DEFAULT_DEFAULT
				&& readPersistedData_file()) {
			return true;
		}

		showDialog = store.getBoolean(IDE.Preferences.SHOW_WORKSPACE_SELECTION_DIALOG);

		int max = store.getInt(IDE.Preferences.MAX_RECENT_WORKSPACES);
		max = Math.max(max, RECENT_MAX_LENGTH);

		String workspacePathPref = store.getString(IDE.Preferences.RECENT_WORKSPACES);
		recentWorkspaces = decodeStoredWorkspacePaths(protocol, max, workspacePathPref);

		showRecentWorkspaces = store.getBoolean(IDE.Preferences.SHOW_RECENT_WORKSPACES);

		return true;
	}

	/**
	 * The the list of recent workspaces must be stored as a string in the preference node.
	 */
    private static String encodeStoredWorkspacePaths(String[] recent) {
		StringBuilder buff = new StringBuilder();

		String path = null;
		for (int i = 0; i < recent.length; ++i) {
			if (recent[i] == null) {
				break;
			}

			if (path != null) {
			}

			path = recent[i];
			buff.append(path);
		}

		return buff.toString();
	}

	/**
	 * The the preference for recent workspaces must be converted from the
	 * storage string into an array.
	 */
	private static String[] decodeStoredWorkspacePaths(int protocol, int max, String prefValue) {
		String[] paths = new String[max];
		if (prefValue == null || prefValue.length() <= 0) {
			return paths;
		}

		String tokens = null;
		switch (protocol) {
			case PERS_ENCODING_VERSION_CONFIG_PREFS_NO_COMMAS :
				break;
			case PERS_ENCODING_VERSION_CONFIG_PREFS :
				tokens = ",; //$NON-NLS-1$
-				break;
-		}
-		if (tokens == null) {
-			// Unknown version? corrupt file? we can't log it because we don't
-			// have a workspace yet...
-			return new String[0];
-		}
-
-		StringTokenizer tokenizer = new StringTokenizer(prefValue, tokens);
-		for (int i = 0; i < paths.length && tokenizer.hasMoreTokens(); ++i) {
-			paths[i] = tokenizer.nextToken();
-		}
-
-		return paths;
-	}
-
-    /**
-	 * Returns true if the protocol used to encode the argument memento is
-	 * compatible with the receiver's implementation and false otherwise.
-	 */
-    private static boolean compatibleFileProtocol(IMemento memento) {
-        IMemento protocolMemento = memento.getChild(XML.PROTOCOL);
-        if (protocolMemento == null) {
-			return false;
-		}
-
-        Integer version = protocolMemento.getInteger(XML.VERSION);
-        return version != null && version.intValue() == PERS_ENCODING_VERSION;
-    }
-
-    /**
-     * The workspace data is stored in the well known file pointed to by the result
-     * of this method.
-     * @param create If the directory and file does not exist this parameter
-     *               controls whether it will be created.
-     * @return An url to the file and null if it does not exist or could not
-     *         be created.
-     */
-    private static URL getPersistenceUrl(URL baseUrl, boolean create) {
-        if (baseUrl == null) {
-			return null;
-		}
-
-        try {
-            // make sure the directory exists
-            URL url = new URL(baseUrl, PERS_FOLDER);
-            File dir = new File(url.getFile());
-            if (!dir.exists() && (!create || !dir.mkdir())) {
-				return null;
-			}
-
-            // make sure the file exists
-            url = new URL(dir.toURL(), PERS_FILENAME);
-            File persFile = new File(url.getFile());
-            if (!persFile.exists() && (!create || !persFile.createNewFile())) {
-				return null;
-			}
-
-            return persFile.toURL();
-        } catch (IOException e) {
-            // cannot log because instance area has not been set
-            return null;
-        }
-    }
-}
diff --git a/bundles/org.eclipse.ui.ide/src/org/eclipse/ui/internal/ide/ChooseWorkspaceDialog.java b/bundles/org.eclipse.ui.ide/src/org/eclipse/ui/internal/ide/ChooseWorkspaceDialog.java
deleted file mode 100644
index 34c1b20b6c..0000000000
--- a/bundles/org.eclipse.ui.ide/src/org/eclipse/ui/internal/ide/ChooseWorkspaceDialog.java
+++ /dev/null
@@ -1,565 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2004, 2016 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *     Jan-Ove Weichel <janove.weichel@vogella.com> - Bugs 411578, 486842, 487673
- *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 492918
- *******************************************************************************/
-package org.eclipse.ui.internal.ide;
-
-import java.io.File;
-import java.util.ArrayList;
-import java.util.Arrays;
-import java.util.HashMap;
-import java.util.List;
-import java.util.Map;
-import java.util.Map.Entry;
-import java.util.function.Function;
-import java.util.regex.Pattern;
-import java.util.stream.Collectors;
-
-import org.eclipse.core.runtime.IProduct;
-import org.eclipse.core.runtime.Platform;
-import org.eclipse.jface.dialogs.Dialog;
-import org.eclipse.jface.dialogs.IDialogConstants;
-import org.eclipse.jface.dialogs.IDialogSettings;
-import org.eclipse.jface.dialogs.IMessageProvider;
-import org.eclipse.jface.dialogs.TitleAreaDialog;
-import org.eclipse.jface.util.Geometry;
-import org.eclipse.jface.window.Window;
-import org.eclipse.osgi.util.NLS;
-import org.eclipse.osgi.util.TextProcessor;
-import org.eclipse.swt.SWT;
-import org.eclipse.swt.events.SelectionAdapter;
-import org.eclipse.swt.events.SelectionEvent;
-import org.eclipse.swt.graphics.Point;
-import org.eclipse.swt.graphics.Rectangle;
-import org.eclipse.swt.layout.GridData;
-import org.eclipse.swt.layout.GridLayout;
-import org.eclipse.swt.layout.RowLayout;
-import org.eclipse.swt.widgets.Button;
-import org.eclipse.swt.widgets.Combo;
-import org.eclipse.swt.widgets.Composite;
-import org.eclipse.swt.widgets.Control;
-import org.eclipse.swt.widgets.DirectoryDialog;
-import org.eclipse.swt.widgets.Label;
-import org.eclipse.swt.widgets.Link;
-import org.eclipse.swt.widgets.Menu;
-import org.eclipse.swt.widgets.MenuItem;
-import org.eclipse.swt.widgets.Monitor;
-import org.eclipse.swt.widgets.Shell;
-import org.eclipse.ui.forms.events.ExpansionAdapter;
-import org.eclipse.ui.forms.events.ExpansionEvent;
-import org.eclipse.ui.forms.widgets.ExpandableComposite;
-import org.eclipse.ui.forms.widgets.Form;
-import org.eclipse.ui.forms.widgets.FormToolkit;
-
-/**
- * A dialog that prompts for a directory to use as a workspace.
- */
-public class ChooseWorkspaceDialog extends TitleAreaDialog {
-
-	private static final String DIALOG_SETTINGS_SECTION = ChooseWorkspaceDialogSettings"; //$NON-NLS-1$

	private ChooseWorkspaceData launchData;

    private Combo text;

    private boolean suppressAskAgain = false;

    private boolean centerOnMonitor = false;

	private Map<String, Composite> recentWorkspacesComposites;

	private Form recentWorkspacesForm;

    /**
     * Create a modal dialog on the arugment shell, using and updating the
     * argument data object.
     * @param parentShell the parent shell for this dialog
     * @param launchData the launch data from past launches
     *
     * @param suppressAskAgain
     *            true means the dialog will not have a "don't ask again" button
     * @param centerOnMonitor indicates whether the dialog should be centered on
     * the monitor or according to it's parent if there is one
     */
    public ChooseWorkspaceDialog(Shell parentShell,
            ChooseWorkspaceData launchData, boolean suppressAskAgain, boolean centerOnMonitor) {
        super(parentShell);
        this.launchData = launchData;
        this.suppressAskAgain = suppressAskAgain;
        this.centerOnMonitor = centerOnMonitor;
    }

    /**
     * Show the dialog to the user (if needed). When this method finishes,
     * #getSelection will return the workspace that should be used (whether it
     * was just selected by the user or some previous default has been used.
     * The parameter can be used to override the users preference.  For example,
     * this is important in cases where the default selection is already in use
     * and the user is forced to choose a different one.
     *
     * @param force
     *            true if the dialog should be opened regardless of the value of
     *            the show dialog checkbox
     */
    public void prompt(boolean force) {
        if (force || launchData.getShowDialog()) {
            open();

            if (getReturnCode() == CANCEL) {
				launchData.workspaceSelected(null);
			}

            return;
        }

        String[] recent = launchData.getRecentWorkspaces();

        String workspace = null;
        if (recent != null && recent.length > 0) {
			workspace = recent[0];
		}
        if (workspace == null || workspace.length() == 0) {
			workspace = launchData.getInitialDefault();
		}
        launchData.workspaceSelected(TextProcessor.deprocess(workspace));
    }

    /**
     * Creates and returns the contents of the upper part of this dialog (above
     * the button bar).
     * <p>
     * The <code>Dialog</code> implementation of this framework method creates
     * and returns a new <code>Composite</code> with no margins and spacing.
     * </p>
     *
     * @param parent the parent composite to contain the dialog area
     * @return the dialog area control
     */
    @Override
	protected Control createDialogArea(Composite parent) {
        String productName = getWindowTitle();

        Composite composite = (Composite) super.createDialogArea(parent);
        setTitle(IDEWorkbenchMessages.ChooseWorkspaceDialog_dialogTitle);
        setMessage(NLS.bind(IDEWorkbenchMessages.ChooseWorkspaceDialog_dialogMessage, productName));

        if (getTitleImageLabel() != null) {
			getTitleImageLabel().setVisible(false);
		}

		boolean createRecentWorkspacesComposite = false;
		if (launchData.getRecentWorkspaces()[0] != null) {
			createRecentWorkspacesComposite = true;
		}
        createWorkspaceBrowseRow(composite);
        if (!suppressAskAgain) {
			createShowDialogButton(composite);
		}
		if (createRecentWorkspacesComposite) {
			createRecentWorkspacesComposite(composite);
		}

			composite.getDisplay().asyncExec(() -> setMessage(IDEWorkbenchMessages.UnsupportedVM_message,
					IMessageProvider.WARNING));
		}

        Dialog.applyDialogFont(composite);
        return composite;
    }

	/**
	 * Returns the title that the dialog (or splash) should have.
	 *
	 * @return the window title
	 * @since 3.4
	 */
	public static String getWindowTitle() {
		String productName = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			productName = product.getName();
		}
		if (productName == null) {
			productName = IDEWorkbenchMessages.ChooseWorkspaceDialog_defaultProductName;
		}
		return productName;
	}

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_dialogName);
		shell.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_ESCAPE) {
				e.detail = SWT.TRAVERSE_NONE;
				cancelPressed();
			}
		});
    }

    /**
     * Notifies that the ok button of this dialog has been pressed.
     * <p>
     * The <code>Dialog</code> implementation of this framework method sets
     * this dialog's return code to <code>Window.OK</code>
     * and closes the dialog. Subclasses may override.
     * </p>
     */
    @Override
	protected void okPressed() {
		workspaceSelected(getWorkspaceLocation());
    }

	/**
	 * Set the selected workspace to the given String and close the dialog
	 *
	 * @param workspace
	 */
	private void workspaceSelected(String workspace) {
		launchData.workspaceSelected(TextProcessor.deprocess(workspace));
		super.okPressed();
	}

	/**
	 * Removes the workspace from RecentWorkspaces
	 *
	 * @param workspace
	 */
	private void removeWorkspaceFromLauncher(String workspace) {
		List<String> recentWorkpaces = new ArrayList<>(Arrays.asList(launchData.getRecentWorkspaces()));
		recentWorkpaces.remove(workspace);
		launchData.setRecentWorkspaces(recentWorkpaces.toArray(new String[0]));
		launchData.writePersistedData();
		recentWorkspacesComposites.get(workspace).dispose();
		recentWorkspacesComposites.remove(workspace);
		if (recentWorkspacesComposites.isEmpty()) {
			recentWorkspacesForm.dispose();
		}
		getShell().layout();
		initializeBounds();
		text.remove(workspace);
		if (text.getText().equals(workspace) || text.getText().isEmpty()) {
			text.setText(TextProcessor
					.process((text.getItemCount() > 0 ? text.getItem(0) : launchData.getInitialDefault())));
		}
	}

	/**
	 * Get the workspace location from the widget.
	 * @return String
	 */
	protected String getWorkspaceLocation() {
		return text.getText();
	}

    @Override
	protected void cancelPressed() {
        launchData.workspaceSelected(null);
        super.cancelPressed();
    }

	/**
	 * The Recent Workspaces area of the dialog is only shown if Recent
	 * Workspaces are defined. It provides a faster way to launch a specific
	 * Workspace
	 */
	private void createRecentWorkspacesComposite(final Composite composite) {
		FormToolkit toolkit = new FormToolkit(composite.getDisplay());
		composite.addDisposeListener(c -> toolkit.dispose());
		recentWorkspacesForm = toolkit.createForm(composite);
		recentWorkspacesForm.setBackground(composite.getBackground());
		recentWorkspacesForm.getBody().setLayout(new GridLayout());
		ExpandableComposite expandableComposite = toolkit.createExpandableComposite(recentWorkspacesForm.getBody(),
				ExpandableComposite.TWISTIE);
		recentWorkspacesForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		expandableComposite.setBackground(composite.getBackground());
		expandableComposite.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_recentWorkspaces);
		expandableComposite.setExpanded(launchData.isShowRecentWorkspaces());
		expandableComposite.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				launchData.setShowRecentWorkspaces(((ExpandableComposite) e.getSource()).isExpanded());
				Point size = getInitialSize();
				Shell shell = getShell();
				shell.setBounds(getConstrainedShellBounds(
						new Rectangle(shell.getLocation().x, shell.getLocation().y, size.x, size.y)));
			}
		});

		Composite panel = new Composite(expandableComposite, SWT.NONE);
		expandableComposite.setClient(panel);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.marginLeft = 14;
		panel.setLayout(layout);
		recentWorkspacesComposites = new HashMap<>(launchData.getRecentWorkspaces().length);
		Map<String, String> uniqueWorkspaceNames = createUniqueWorkspaceNameMap();

		List<String> recentWorkspacesList = Arrays.asList(launchData.getRecentWorkspaces()).stream()
				.filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList());
		List<Entry<String, String>> sortedList = uniqueWorkspaceNames.entrySet().stream().sorted((e1, e2) -> Integer
				.compare(recentWorkspacesList.indexOf(e1.getValue()), recentWorkspacesList.indexOf(e2.getValue())))
				.collect(Collectors.toList());

		for (Entry<String, String> uniqueWorkspaceEntry : sortedList) {
			final String recentWorkspace = uniqueWorkspaceEntry.getValue();

			Composite recentWorkspacePanel = new Composite(panel, SWT.NONE);
			recentWorkspacesComposites.put(recentWorkspace, recentWorkspacePanel);
			GridLayout recentWorkspacePanelLayout = new GridLayout(3, false);
			recentWorkspacePanel.setLayout(recentWorkspacePanelLayout);

			Link link = new Link(recentWorkspacePanel, SWT.WRAP);
			link.setLayoutData(new GridData(500, SWT.DEFAULT));
			link.setToolTipText(recentWorkspace);

			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					workspaceSelected(recentWorkspace);
				}
			});

			Menu menu = new Menu(link);
			MenuItem forgetItem = new MenuItem(menu, SWT.PUSH);
			forgetItem.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_removeWorkspaceSelection);
			forgetItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					removeWorkspaceFromLauncher(recentWorkspace);
				}
			});
			link.setMenu(menu);
		}
	}

	/**
	 * Creates a map with unique WorkspaceNames for the
	 * RecentWorkspacesComposite. The values are full absolute paths of recently
	 * used workspaces, the keys are unique segments somehow made from the
	 * values.
	 */
	private Map<String, String> createUniqueWorkspaceNameMap() {
		final String fileSeparator = File.separator;
		Map<String, String> uniqueWorkspaceNameMap = new HashMap<>();

		List<String[]> splittedWorkspaceNames = Arrays.asList(launchData.getRecentWorkspaces()).stream()
				.filter(s -> s != null && !s.isEmpty()).map(s -> s.split(Pattern.quote(fileSeparator)))
				.collect(Collectors.toList());

		for (int i = 1; !splittedWorkspaceNames.isEmpty(); i++) {
			final int c = i;

			Function<String[], String> stringArraytoName = s -> String.join(fileSeparator,
					s.length < c ? s : Arrays.copyOfRange(s, s.length - c, s.length));

			List<String> uniqueNames = splittedWorkspaceNames.stream().map(stringArraytoName)
					.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream()
					.filter(e -> e.getValue() == 1).map(e -> e.getKey()).collect(Collectors.toList());

			splittedWorkspaceNames.removeIf(a -> {
				String joined = stringArraytoName.apply(a);
				if (uniqueNames.contains(joined)) {
					uniqueWorkspaceNameMap.put(joined, String.join(fileSeparator, a));
					return true;
				}
				return false;
			});
		}
		return uniqueWorkspaceNameMap;
	}

    /**
     * The main area of the dialog is just a row with the current selection
     * information and a drop-down of the most recently used workspaces.
     */
    private void createWorkspaceBrowseRow(Composite parent) {
        Composite panel = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        panel.setLayout(layout);
        panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        panel.setFont(parent.getFont());

        Label label = new Label(panel, SWT.NONE);
        label.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_workspaceEntryLabel);

        text = new Combo(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
        text.setFocus();
        text.setLayoutData(new GridData(400, SWT.DEFAULT));
        text.addModifyListener(e -> {
			Button okButton = getButton(Window.OK);
			if(okButton != null && !okButton.isDisposed()) {
				boolean nonWhitespaceFound = false;
				String characters = getWorkspaceLocation();
				for (int i = 0; !nonWhitespaceFound
						&& i < characters.length(); i++) {
					if (!Character.isWhitespace(characters.charAt(i))) {
						nonWhitespaceFound = true;
					}
				}
				okButton.setEnabled(nonWhitespaceFound);
			}
		});
        setInitialTextValues(text);

        Button browseButton = new Button(panel, SWT.PUSH);
        browseButton.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_browseLabel);
        setButtonLayoutData(browseButton);
        GridData data = (GridData) browseButton.getLayoutData();
        data.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
        browseButton.setLayoutData(data);
        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
                dialog.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_directoryBrowserTitle);
                dialog.setMessage(IDEWorkbenchMessages.ChooseWorkspaceDialog_directoryBrowserMessage);
                dialog.setFilterPath(getInitialBrowsePath());
                String dir = dialog.open();
                if (dir != null) {
					text.setText(TextProcessor.process(dir));
				}
            }
        });
    }

    /**
     * Return a string containing the path that is closest to the current
     * selection in the text widget. This starts with the current value and
     * works toward the root until there is a directory for which File.exists
     * returns true. Return the current working dir if the text box does not
     * contain a valid path.
     *
     * @return closest parent that exists or an empty string
     */
    private String getInitialBrowsePath() {
        File dir = new File(getWorkspaceLocation());
        while (dir != null && !dir.exists()) {
			dir = dir.getParentFile();
		}

        return dir != null ? dir.getAbsolutePath() : System
    }

	/*
	 * see org.eclipse.jface.Window.getInitialLocation()
	 */
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Composite parent = getShell().getParent();

		if (!centerOnMonitor || parent == null) {
			return super.getInitialLocation(initialSize);
		}

		Monitor monitor = parent.getMonitor();
		Rectangle monitorBounds = monitor.getClientArea();
		Point centerPoint = Geometry.centerPoint(monitorBounds);

		return new Point(centerPoint.x - (initialSize.x / 2), Math.max(
				monitorBounds.y, Math.min(centerPoint.y
						- (initialSize.y * 2 / 3), monitorBounds.y
						+ monitorBounds.height - initialSize.y)));
	}

    /**
     * The show dialog button allows the user to choose to neven be nagged again.
     */
    private void createShowDialogButton(Composite parent) {
        Composite panel = new Composite(parent, SWT.NONE);
        panel.setFont(parent.getFont());

        GridLayout layout = new GridLayout(1, false);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        panel.setLayout(layout);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.verticalAlignment = GridData.END;
        panel.setLayoutData(data);

        Button button = new Button(panel, SWT.CHECK);
        button.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_useDefaultMessage);
        button.setSelection(!launchData.getShowDialog());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                launchData.toggleShowDialog();
            }
        });
    }

    private void setInitialTextValues(Combo text) {
        String[] recentWorkspaces = launchData.getRecentWorkspaces();
        for (int i = 0; i < recentWorkspaces.length; ++i) {
			if (recentWorkspaces[i] != null) {
				text.add(recentWorkspaces[i]);
			}
		}

        text.setText(TextProcessor.process((text.getItemCount() > 0 ? text
				.getItem(0) : launchData.getInitialDefault())));
    }

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		if (centerOnMonitor) {
			return null;
		}

        IDialogSettings settings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
        if (section == null) {
            section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
        }
        return section;
	}

}
