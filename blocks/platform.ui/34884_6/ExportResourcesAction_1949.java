package org.eclipse.ui.actions;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.ChangeToPerspectiveMenu;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.ReopenEditorMenu;
import org.eclipse.ui.internal.ShowInMenu;
import org.eclipse.ui.internal.ShowViewMenu;
import org.eclipse.ui.internal.SwitchToWindowMenu;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.HelpSearchContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

public abstract class ContributionItemFactory {

    private final String contributionItemId;

    protected ContributionItemFactory(String contributionItemId) {
        this.contributionItemId = contributionItemId;
    }

    public abstract IContributionItem create(IWorkbenchWindow window);

    public String getId() {
        return contributionItemId;
    }

	public static final ContributionItemFactory PIN_EDITOR = new ContributionItemFactory(
			"pinEditor") { //$NON-NLS-1$
		private static final String COMMAND_ID = IWorkbenchCommandConstants.WINDOW_PIN_EDITOR;

		@Override
		public IContributionItem create(final IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}

			CommandContributionItemParameter parameter = new CommandContributionItemParameter(
					window,
					COMMAND_ID,
					COMMAND_ID,
					null,
					WorkbenchImages
							.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_PIN_EDITOR),
					WorkbenchImages
							.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_PIN_EDITOR_DISABLED),
					null, null, null, WorkbenchMessages.PinEditorAction_toolTip, // Local workaround for http://bugs.eclipse.org/387583
					CommandContributionItem.STYLE_CHECK, null, false);
			final IPropertyChangeListener[] perfs = new IPropertyChangeListener[1];
			final IPartListener partListener = new IPartListener() {

				@Override
				public void partOpened(IWorkbenchPart part) {
				}

				@Override
				public void partDeactivated(IWorkbenchPart part) {
				}

				@Override
				public void partClosed(IWorkbenchPart part) {
				}

				@Override
				public void partBroughtToTop(IWorkbenchPart part) {
					if (!(part instanceof IEditorPart)) {
						return;
					}
					ICommandService commandService = window
							.getService(ICommandService.class);

					commandService.refreshElements(COMMAND_ID, null);
				}

				@Override
				public void partActivated(IWorkbenchPart part) {
				}
			};
			window.getPartService().addPartListener(partListener);
			final CommandContributionItem action = new CommandContributionItem(
					parameter) {
				@Override
				public void dispose() {
					WorkbenchPlugin.getDefault().getPreferenceStore()
							.removePropertyChangeListener(perfs[0]);
					window.getPartService().removePartListener(partListener);
					super.dispose();
				}
			};

			perfs[0] = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty().equals(
							IPreferenceConstants.REUSE_EDITORS_BOOLEAN)) {
						if (action.getParent() != null) {
							IPreferenceStore store = WorkbenchPlugin
									.getDefault().getPreferenceStore();
							boolean reuseEditors = store
									.getBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN);
							action.setVisible(reuseEditors);
							action.getParent().markDirty();
							if (window.getShell() != null
									&& !window.getShell().isDisposed()) {
								window.getShell().getDisplay().syncExec(
										new Runnable() {
											@Override
											public void run() {
												action.getParent()
														.update(false);
											}
										});
							}
						}
					}
				}
			};
			WorkbenchPlugin.getDefault().getPreferenceStore()
					.addPropertyChangeListener(perfs[0]);
			action.setVisible(WorkbenchPlugin.getDefault().getPreferenceStore()
.getBoolean(
					IPreferenceConstants.REUSE_EDITORS_BOOLEAN));
			return action;
		}
	};
	
    public static final ContributionItemFactory OPEN_WINDOWS = new ContributionItemFactory(
            "openWindows") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new SwitchToWindowMenu(window, getId(), true);
        }
    };

    public static final ContributionItemFactory VIEWS_SHORTLIST = new ContributionItemFactory(
            "viewsShortlist") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new ShowViewMenu(window, getId());
        }
    };

    public static final ContributionItemFactory VIEWS_SHOW_IN = new ContributionItemFactory(
            "viewsShowIn") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            
            ShowInMenu showInMenu = new ShowInMenu();
            showInMenu.setId(getId());
            showInMenu.initialize(window);
			return showInMenu;
        }
    };

    public static final ContributionItemFactory REOPEN_EDITORS = new ContributionItemFactory(
            "reopenEditors") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new ReopenEditorMenu(window, getId(), true);
        }
    };

    public static final ContributionItemFactory PERSPECTIVES_SHORTLIST = new ContributionItemFactory(
            "perspectivesShortlist") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new ChangeToPerspectiveMenu(window, getId());
        }
    };
    
    public static final ContributionItemFactory NEW_WIZARD_SHORTLIST = new ContributionItemFactory(
            "newWizardShortlist") { //$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new BaseNewWizardMenu(window, getId());
        }
    };
    
    public static final ContributionItemFactory HELP_SEARCH = new ContributionItemFactory(
            "helpSearch") {//$NON-NLS-1$
        @Override
		public IContributionItem create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            return new HelpSearchContributionItem(window, getId());
        }
    };

    
}
