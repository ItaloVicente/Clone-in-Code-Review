package org.eclipse.e4.ui.progress.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.ui.progress.e4new.ExternalServices;
import org.eclipse.e4.ui.progress.legacy.PlatformUI;
import org.eclipse.e4.ui.progress.legacy.Policy;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ProgressMonitorJobsDialog extends ProgressMonitorDialog {
    private DetailedProgressViewer viewer;

    private int viewerHeight = -1;

    Composite viewerComposite;

    private Button detailsButton;

    private long watchTime = -1;

    protected boolean alreadyClosed = false;

    private IProgressMonitor wrapperedMonitor;

    protected boolean enableDetailsButton = false;

    public ProgressMonitorJobsDialog(Shell parent) {
        super(parent);
    }

    protected Control createDialogArea(Composite parent) {
        Composite top = (Composite) super.createDialogArea(parent);
        createExtendedDialogArea(parent);
        return top;
    }

	protected void createExtendedDialogArea(Composite parent) {
		viewerComposite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        viewerComposite.setLayout(layout);
        GridData viewerData = new GridData(GridData.FILL_BOTH);
        viewerData.horizontalSpan = 2;
        viewerData.heightHint = 0;
        viewerComposite.setLayoutData(viewerData);
	}

    void handleDetailsButtonSelect() {
        Shell shell = getShell();
        Point shellSize = shell.getSize();
        Composite composite = (Composite) getDialogArea();
        if (viewer != null) {
            viewer.getControl().dispose();
            viewer = null;
            composite.layout();
            shell.setSize(shellSize.x, shellSize.y - viewerHeight);
            detailsButton.setText(ProgressMessages.ProgressMonitorJobsDialog_DetailsTitle);
        } else {
            if (ProgressManager.getInstance().getRootElements(Policy.DEBUG_SHOW_ALL_JOBS).length == 0) {
                detailsButton.setEnabled(false);
                return;
            }

            viewer = new DetailedProgressViewer(viewerComposite, SWT.MULTI
                    | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
            viewer.setComparator(new ViewerComparator() {
                public int compare(Viewer testViewer, Object e1, Object e2) {
                    return ((Comparable) e1).compareTo(e2);
                }
            });

            viewer.setContentProvider(new ProgressViewerContentProvider(viewer,true,false){
            	public Object[] getElements(Object inputElement) {
            		return super.getElements(inputElement);
            	}}
            );
            
            viewer.setLabelProvider(new ProgressLabelProvider());
            viewer.setInput(this);
            GridData viewerData = new GridData(GridData.FILL_BOTH);
            viewer.getControl().setLayoutData(viewerData);
            GridData viewerCompositeData = (GridData) viewerComposite.getLayoutData();
            viewerCompositeData.heightHint = convertHeightInCharsToPixels(10);
            viewerComposite.layout(true);
            viewer.getControl().setVisible(true);
            viewerHeight = viewerComposite.computeTrim(0, 0, 0, viewerCompositeData.heightHint).height;
            detailsButton.setText(ProgressMessages.ProgressMonitorJobsDialog_HideTitle); 
            shell.setSize(shellSize.x, shellSize.y + viewerHeight);
        }
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        createDetailsButton(parent);
    }

    protected void createSpacer(Composite parent) {
        Label spacer = new Label(parent, SWT.NONE);
        spacer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
    }

    protected void createDetailsButton(Composite parent) {
        detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
                ProgressMessages.ProgressMonitorJobsDialog_DetailsTitle, 
                false);
        detailsButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                handleDetailsButtonSelect();
            }
        });
        detailsButton.setCursor(arrowCursor);
        detailsButton.setEnabled(enableDetailsButton);
    }

    protected Control createButtonBar(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 1; // this is incremented by createButton
        layout.makeColumnsEqualWidth = false;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        data.horizontalAlignment = GridData.END;
        data.grabExcessHorizontalSpace = true;
        composite.setLayoutData(data);
        composite.setFont(parent.getFont());
        if (arrowCursor == null) {
			arrowCursor = new Cursor(parent.getDisplay(), SWT.CURSOR_ARROW);
		}
        createButtonsForButtonBar(composite);
        return composite;
    }

    protected void clearCursors() {
        if (detailsButton != null && !detailsButton.isDisposed()) {
            detailsButton.setCursor(null);
        }
        super.clearCursors();
    }

    protected void updateForSetBlocked(IStatus reason) {
    	if(alreadyClosed)
    		return;
    	
        super.updateForSetBlocked(reason);
        enableDetails(true);
        if (viewer == null) {
			handleDetailsButtonSelect();
		}
    }

    public void run(boolean fork, boolean cancelable,
            IRunnableWithProgress runnable) throws InvocationTargetException,
            InterruptedException {
        if (!fork) {
            enableDetails(false);
        }
        super.run(fork, cancelable, runnable);
    }

    protected void enableDetails(boolean enableState) {
        if (detailsButton == null) {
			enableDetailsButton = enableState;
		} else {
			detailsButton.setEnabled(enableState);
		}
    }

    public void watchTicks() {
        watchTime = System.currentTimeMillis();
    }

    public void createWrapperedMonitor() {
        wrapperedMonitor = new IProgressMonitorWithBlocking() {

            IProgressMonitor superMonitor = ProgressMonitorJobsDialog.super
                    .getProgressMonitor();

            public void beginTask(String name, int totalWork) {
                superMonitor.beginTask(name, totalWork);
                checkTicking();
            }

            private void checkTicking() {
                if (watchTime < 0) {
					return;
				}
                if ((System.currentTimeMillis() - watchTime) > ProgressManager
                        .getInstance().getLongOperationTime()) {
                    watchTime = -1;
                    openDialog();
                }
            }

            private void openDialog() {
                if (!PlatformUI.isWorkbenchRunning()) {
					return;
				}
            	ExternalServices.getUiSynchronize().syncExec(new Runnable() {
                    public void run() {
						 if (!ProgressManagerUtil.safeToOpen(ProgressMonitorJobsDialog.this,null)){
							  watchTicks();
							  return;
						 }

                        if (!alreadyClosed) {
							open();
						}
                    }
                });
            }

            public void done() {
                superMonitor.done();
                checkTicking();
            }

            public void internalWorked(double work) {
                superMonitor.internalWorked(work);
                checkTicking();
            }

            public boolean isCanceled() {
                return superMonitor.isCanceled();
            }

            public void setCanceled(boolean value) {
                superMonitor.setCanceled(value);

            }

            public void setTaskName(String name) {
                superMonitor.setTaskName(name);
                checkTicking();

            }

            public void subTask(String name) {
                superMonitor.subTask(name);
                checkTicking();
            }

            public void worked(int work) {
                superMonitor.worked(work);
                checkTicking();

            }

            public void clearBlocked() {
                if (superMonitor instanceof IProgressMonitorWithBlocking) {
					((IProgressMonitorWithBlocking) superMonitor)
                            .clearBlocked();
				}

            }

            public void setBlocked(IStatus reason) {
                openDialog();
                if (superMonitor instanceof IProgressMonitorWithBlocking) {
					((IProgressMonitorWithBlocking) superMonitor)
                            .setBlocked(reason);
				}

            }

        };
    }

    public IProgressMonitor getProgressMonitor() {
        if (wrapperedMonitor == null) {
			createWrapperedMonitor();
		}
        return wrapperedMonitor;
    }

    public boolean close() {
        alreadyClosed = true;//As this sometimes delayed cache if it was already closed
        boolean result = super.close();
        if (!result) {//If it fails reset the flag
            alreadyClosed = false;
        }
        return result;
    }
    
    protected boolean isResizable() {
    	return true;
    }
}
