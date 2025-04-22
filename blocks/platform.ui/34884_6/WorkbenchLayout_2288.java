package org.eclipse.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityView;
import org.eclipse.ui.internal.intro.IIntroConstants;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.internal.intro.IntroMessages;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IntroContentDetector;

public class WorkbenchIntroManager implements IIntroManager {	
	
    private final Workbench workbench;

    WorkbenchIntroManager(Workbench workbench) {
        this.workbench = workbench;
        workbench.getExtensionTracker().registerHandler(new IExtensionChangeHandler(){
            
            @Override
			public void addExtension(IExtensionTracker tracker,IExtension extension) {
            }
            
			@Override
			public void removeExtension(IExtension source, Object[] objects) {
                for (int i = 0; i < objects.length; i++) {
                    if (objects[i] instanceof IIntroPart) {
                        closeIntro((IIntroPart) objects[i]);
                    }
                }
				
			}}, null);
        
    }

    private IIntroPart introPart;

    @Override
	public boolean closeIntro(IIntroPart part) {
        if (introPart == null || !introPart.equals(part)) {
			return false;
		}

        IViewPart introView = getViewIntroAdapterPart();
        if (introView != null) {
            IWorkbenchPage page = introView.getSite().getPage();
            IViewReference reference = page
                    .findViewReference(IIntroConstants.INTRO_VIEW_ID);
            page.hideView(introView);
            if (reference == null || reference.getPart(false) == null) {
                introPart = null;                
                return true;
            }
            return false;
        }
        
		introPart = null;
        
        return true;
    }

    @Override
	public IIntroPart showIntro(IWorkbenchWindow preferredWindow,
            boolean standby) {
        if (preferredWindow == null) {
			preferredWindow = this.workbench.getActiveWorkbenchWindow();
		}

        if (preferredWindow == null) {
			return null;
		}

        ViewIntroAdapterPart viewPart = getViewIntroAdapterPart();
        if (viewPart == null) {
            createIntro(preferredWindow);
        } else {
            try {
                IWorkbenchPage page = viewPart.getSite().getPage();
                IWorkbenchWindow window = page.getWorkbenchWindow();
                if (!window.equals(preferredWindow)) {
                    window.getShell().setActive();
                }

                page.showView(IIntroConstants.INTRO_VIEW_ID);
            } catch (PartInitException e) {
                WorkbenchPlugin
                        .log(
                                "Could not open intro", new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR, "Could not open intro", e)); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        setIntroStandby(introPart, standby);
        return introPart;
    }

        ViewIntroAdapterPart viewPart = getViewIntroAdapterPart();
        if (viewPart == null) {
			return false;
		}

        IWorkbenchWindow window = viewPart.getSite().getWorkbenchWindow();
        if (window.equals(testWindow)) {
            return true;
        }
        return false;
    }

    private void createIntro(IWorkbenchWindow preferredWindow) {
        if (this.workbench.getIntroDescriptor() == null) {
			return;
		}

        IWorkbenchPage workbenchPage = preferredWindow.getActivePage();
        if (workbenchPage == null) {
			return;
		}
        try {
            workbenchPage.showView(IIntroConstants.INTRO_VIEW_ID);
        } catch (PartInitException e) {
            WorkbenchPlugin
                    .log(
                            IntroMessages.Intro_could_not_create_part, new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR, IntroMessages.Intro_could_not_create_part, e));
        }
    }

    @Override
	public void setIntroStandby(IIntroPart part, boolean standby) {
        if (introPart == null || !introPart.equals(part)) {
			return;
		}

		ViewIntroAdapterPart viewIntroAdapterPart = getViewIntroAdapterPart();
		if (viewIntroAdapterPart == null) {
			return;
		}

		MPartStack introStack = getIntroStack(viewIntroAdapterPart);
		if (introStack == null)
			return;

		boolean isMaximized = isIntroMaximized(viewIntroAdapterPart);
		if (!isMaximized && !standby)
			introStack.getTags().add(IPresentationEngine.MAXIMIZED);
		else if (isMaximized && standby)
			introStack.getTags().remove(IPresentationEngine.MAXIMIZED);
	}

	private MPartStack getIntroStack(ViewIntroAdapterPart introAdapter) {
		ViewSite site = (ViewSite) introAdapter.getViewSite();

		MPart introModelPart = site.getModel();
		MUIElement introPartParent = introModelPart.getCurSharedRef().getParent();
		if (introPartParent instanceof MPartStack)
			return (MPartStack) introPartParent;

		return null;
	}

	private boolean isIntroMaximized(ViewIntroAdapterPart introAdapter) {
		MPartStack introStack = getIntroStack(introAdapter);
		if (introStack == null)
			return false;

		return introStack.getTags().contains(IPresentationEngine.MAXIMIZED);
    }

    @Override
	public boolean isIntroStandby(IIntroPart part) {
        if (introPart == null || !introPart.equals(part)) {
			return false;
		}

        ViewIntroAdapterPart viewIntroAdapterPart = getViewIntroAdapterPart();
        if (viewIntroAdapterPart == null) {
			return false;
		}

		return !isIntroMaximized(viewIntroAdapterPart);
    }

    @Override
	public IIntroPart getIntro() {
        return introPart;
    }

		IWorkbenchWindow[] windows = this.workbench.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			WorkbenchWindow window = (WorkbenchWindow) windows[i];
			MUIElement introPart = window.modelService
.find(IIntroConstants.INTRO_VIEW_ID,
					window.getModel());
			if (introPart instanceof MPlaceholder) {
				MPlaceholder introPH = (MPlaceholder) introPart;
				MPart introModelPart = (MPart) introPH.getRef();
				CompatibilityView compatView = (CompatibilityView) introModelPart.getObject();
				if (compatView != null) {
					Object obj = compatView.getPart();
					if (obj instanceof ViewIntroAdapterPart)
						return (ViewIntroAdapterPart) obj;
				}
			}
		}
        return null;
    }

        IntroDescriptor introDescriptor = workbench.getIntroDescriptor();
		introPart = introDescriptor == null ? null
                : introDescriptor.createIntro();
        if (introPart != null) {
        	workbench.getExtensionTracker().registerObject(
					introDescriptor.getConfigurationElement()
							.getDeclaringExtension(), introPart,
					IExtensionTracker.REF_WEAK);
        }
    	return introPart;
    }

    @Override
	public boolean hasIntro() {
        return workbench.getIntroDescriptor() != null;
    }
    
    @Override
	public boolean isNewContentAvailable() {
		IntroDescriptor introDescriptor = workbench.getIntroDescriptor();
		if (introDescriptor == null) {
			return false;
		}
		try {
			IntroContentDetector contentDetector = introDescriptor
					.getIntroContentDetector();
			if (contentDetector != null) {
				return contentDetector.isNewContentAvailable();
			}
		} catch (CoreException ex) {
			WorkbenchPlugin.log(new Status(IStatus.WARNING,
					WorkbenchPlugin.PI_WORKBENCH, IStatus.WARNING,
					"Could not load intro content detector", ex)); //$NON-NLS-1$
		}
		return false;
	}
}
