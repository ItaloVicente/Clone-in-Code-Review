/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Mickael Istria (Red Hat Inc.) - [486901] Avoid blocking URL.equals
 *******************************************************************************/
package org.eclipse.ui.actions;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.ProductProperties;
import org.eclipse.ui.internal.ide.AboutInfo;
import org.eclipse.ui.internal.ide.FeatureSelectionDialog;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.dialogs.WelcomeEditorInput;

/**
 * The quick start (Welcome...) action.
 *
 * @deprecated the IDE now uses the new intro mechanism
 */
@Deprecated
public class QuickStartAction extends Action implements
        ActionFactory.IWorkbenchAction {


    /**
     * The workbench window; or <code>null</code> if this
     * action has been <code>dispose</code>d.
     */
    private IWorkbenchWindow workbenchWindow;

    /**
     * Create an instance of this class.
     * <p>
     * This consructor added to support calling the action from the welcome
     * page.
     * </p>
     */
    public QuickStartAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }

    /**
     * Creates an instance of this action, for use in the given window.
     * @param window the window
     */
    public QuickStartAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.QuickStart_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setToolTipText(IDEWorkbenchMessages.QuickStart_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IIDEHelpContextIds.QUICK_START_ACTION);
		setActionDefinitionId(IWorkbenchCommandConstants.HELP_WELCOME);
    }

    /**
     * The user has invoked this action.  Prompts for a feature with a welcome page,
     * then opens the corresponding welcome page.
     */
    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        try {
            AboutInfo feature = promptForFeature();
            if (feature != null) {
                openWelcomePage(feature);
            }
        } catch (WorkbenchException e) {
            ErrorDialog.openError(workbenchWindow.getShell(),
                    IDEWorkbenchMessages.QuickStartAction_errorDialogTitle,
                    IDEWorkbenchMessages.QuickStartAction_infoReadError,
                    e.getStatus());
        }
    }

    /**
     * Prompts the user for a feature that has a welcome page.
     *
     * @return the chosen feature, or <code>null</code> if none was chosen
     */
    private AboutInfo promptForFeature() throws WorkbenchException {
        ArrayList welcomeFeatures = new ArrayList();

        URL productUrl = null;
        IProduct product = Platform.getProduct();
        if (product != null) {
            productUrl = ProductProperties.getWelcomePageUrl(product);
            welcomeFeatures.add(new AboutInfo(product));
        }

        AboutInfo[] features = IDEWorkbenchPlugin.getDefault()
                .getFeatureInfos();
        for (AboutInfo feature : features) {
            URL url = feature.getWelcomePageURL();
            if (url != null && (productUrl == null || !url.toString().equals(productUrl.toString()))) {
				welcomeFeatures.add(feature);
			}
        }

        Shell shell = workbenchWindow.getShell();

        if (welcomeFeatures.size() == 0) {
            MessageDialog.openInformation(shell, IDEWorkbenchMessages.QuickStartMessageDialog_title,
                    IDEWorkbenchMessages.QuickStartMessageDialog_message);
            return null;
        }

        features = new AboutInfo[welcomeFeatures.size()];
        welcomeFeatures.toArray(features);

        FeatureSelectionDialog d = new FeatureSelectionDialog(shell, features,
                product == null ? null : product.getId(), IDEWorkbenchMessages.WelcomePageSelectionDialog_title,
                IDEWorkbenchMessages.WelcomePageSelectionDialog_message,
                IIDEHelpContextIds.WELCOME_PAGE_SELECTION_DIALOG);
        if (d.open() != Window.OK || d.getResult().length != 1) {
			return null;
		}
        return (AboutInfo) d.getResult()[0];
    }

    /**
     * Opens the welcome page for the given feature.
     *
     * @param featureId the about info for the feature
     * @return <code>true</code> if successful, <code>false</code> otherwise
     * @throws WorkbenchException
     */
    public boolean openWelcomePage(String featureId) throws WorkbenchException {
        AboutInfo feature = findFeature(featureId);
        if (feature == null || feature.getWelcomePageURL() == null) {
            return false;
        }
        return openWelcomePage(feature);
    }

    /**
     * Returns the about info for the feature with the given id, or <code>null</code>
     * if there is no such feature.
     *
     * @return the about info for the feature with the given id, or <code>null</code>
     *   if there is no such feature.
     */
    private AboutInfo findFeature(String featureId) throws WorkbenchException {
        AboutInfo[] features = IDEWorkbenchPlugin.getDefault()
                .getFeatureInfos();
        for (AboutInfo info : features) {
            if (info.getFeatureId().equals(featureId)) {
                return info;
            }
        }
        return null;
    }

    /**
     * Opens the welcome page for a feature.
     *
     * @param feature the about info for the feature
     * @return <code>true</code> if successful, <code>false</code> otherwise
     */
    private boolean openWelcomePage(AboutInfo feature) {
        IWorkbenchPage page = null;

        String perspectiveId = feature.getWelcomePerspectiveId();

        if (perspectiveId == null) {
            page = workbenchWindow.getActivePage();

            if (page == null || page.getPerspective() == null) {
                perspectiveId = PlatformUI.getWorkbench()
                        .getPerspectiveRegistry().getDefaultPerspective();
            }
        }

        if (perspectiveId != null) {
            try {
                page = PlatformUI.getWorkbench().showPerspective(perspectiveId,
                        workbenchWindow);
            } catch (WorkbenchException e) {
                IDEWorkbenchPlugin
						.log("Error opening perspective: " + perspectiveId, e.getStatus()); //$NON-NLS-1$
                return false;
            }
        }

        if (page == null) {
        	return false;
        }

        page.setEditorAreaVisible(true);

        WelcomeEditorInput input = new WelcomeEditorInput(feature);

        IEditorPart editor = page.findEditor(input);
        if (editor != null) {
            page.activate(editor);
            return true;
        }

        try {
            page.openEditor(input, EDITOR_ID);
        } catch (PartInitException e) {
            IDEWorkbenchPlugin
                    .log("Error opening welcome editor for feature: " + feature.getFeatureId(), e); //$NON-NLS-1$
            IStatus status = new Status(
                    IStatus.ERROR,
                    IDEWorkbenchPlugin.IDE_WORKBENCH,
                    1,
                    IDEWorkbenchMessages.QuickStartAction_openEditorException, e);
            ErrorDialog
                    .openError(
                            workbenchWindow.getShell(),
                            IDEWorkbenchMessages.Workbench_openEditorErrorDialogTitle,
                            IDEWorkbenchMessages.Workbench_openEditorErrorDialogMessage,
                            status);
            return false;
        }
        return true;
    }

    @Override
	public void dispose() {
        if (workbenchWindow == null) {
            return;
        }
        workbenchWindow = null;
    }

}
