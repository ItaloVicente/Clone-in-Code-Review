package org.eclipse.ui.actions;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.CloseAllSavedAction;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.NavigationHistoryAction;
import org.eclipse.ui.internal.OpenPreferencesAction;
import org.eclipse.ui.internal.ToggleEditorsVisibilityAction;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.internal.actions.DynamicHelpAction;
import org.eclipse.ui.internal.actions.HelpContentsAction;
import org.eclipse.ui.internal.actions.HelpSearchAction;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.internal.intro.IntroMessages;

public abstract class ActionFactory {

    public interface IWorkbenchAction extends IAction {
        public void dispose();
    }
    
    private static class WorkbenchCommandAction extends CommandAction implements
			IWorkbenchAction {
		public WorkbenchCommandAction(String commandIdIn,
				IWorkbenchWindow window) {
			super(window, commandIdIn);
		}
	}

    public static final ActionFactory ABOUT = new ActionFactory("about", //$NON-NLS-1$
    		IWorkbenchCommandConstants.HELP_ABOUT) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}

			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);

			action.setId(getId());
			IProduct product = Platform.getProduct();
			String productName = null;
			if (product != null) {
				productName = product.getName();
			}
			if (productName == null) {
				productName = ""; //$NON-NLS-1$
			}

			action.setText(NLS.bind(WorkbenchMessages.AboutAction_text,
					productName));
			action.setToolTipText(NLS.bind(
					WorkbenchMessages.AboutAction_toolTip, productName));
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.ABOUT_ACTION);
			return action;
		}
	};

    public static final ActionFactory ACTIVATE_EDITOR = new ActionFactory(
            "activateEditor", IWorkbenchCommandConstants.WINDOW_ACTIVATE_EDITOR) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.ActivateEditorAction_text);
			action
					.setToolTipText(WorkbenchMessages.ActivateEditorAction_toolTip);
			return action;
		}
    };

    public static final ActionFactory BACK = new ActionFactory("back", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_BACK) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_back);
            action.setToolTipText(WorkbenchMessages.Workbench_backToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory BACKWARD_HISTORY = new ActionFactory(
            "backardHistory", IWorkbenchCommandConstants.NAVIGATE_BACKWARD_HISTORY) {//$NON-NLS-1$
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new NavigationHistoryAction(window, false);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory CLOSE = new ActionFactory("close",//$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_CLOSE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CloseEditorAction_text);
            action.setToolTipText(WorkbenchMessages.CloseEditorAction_toolTip);
            return action;
        }
    };

    public static final ActionFactory CLOSE_ALL = new ActionFactory("closeAll",//$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_CLOSE_ALL) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CloseAllAction_text);
            action.setToolTipText(WorkbenchMessages.CloseAllAction_toolTip);
            return action;
        }
    };

    public static final ActionFactory CLOSE_OTHERS = new ActionFactory("closeOthers",//$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_CLOSE_OTHERS) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CloseOthersAction_text);
			action.setToolTipText(WorkbenchMessages.CloseOthersAction_toolTip);
			return action;
        }
    };

    public static final ActionFactory CLOSE_ALL_PERSPECTIVES = new ActionFactory(
            "closeAllPerspectives", IWorkbenchCommandConstants.WINDOW_CLOSE_ALL_PERSPECTIVES) {//$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            
            action.setId(getId());
            action.setText(WorkbenchMessages.CloseAllPerspectivesAction_text);
            action.setToolTipText(WorkbenchMessages.CloseAllPerspectivesAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.CLOSE_ALL_PAGES_ACTION);
            
            return action;
        }
    };

    public static final ActionFactory CLOSE_ALL_SAVED = new ActionFactory(
            "closeAllSaved") {//$NON-NLS-1$
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new CloseAllSavedAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory CLOSE_PERSPECTIVE = new ActionFactory(
    "closePerspective", IWorkbenchCommandConstants.WINDOW_CLOSE_PERSPECTIVE) {//$NON-NLS-1$

    	@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
    		if (window == null) {
    			throw new IllegalArgumentException();
    		}
    		WorkbenchCommandAction action = new WorkbenchCommandAction(
    				getCommandId(), window);

    		action.setId(getId());
    		action.setText(WorkbenchMessages.
    				ClosePerspectiveAction_text);
    		action.setToolTipText(WorkbenchMessages.
    				ClosePerspectiveAction_toolTip);
    		window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.CLOSE_PAGE_ACTION);
    		return action;
    	}
    };

    public static final ActionFactory INTRO = new ActionFactory("intro", //$NON-NLS-1$
    		IWorkbenchCommandConstants.HELP_WELCOME) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }

			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);

			action.setId(getId());
			action.setText(IntroMessages.Intro_action_text);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.INTRO_ACTION);
			IntroDescriptor introDescriptor = ((Workbench) window.getWorkbench())
					.getIntroDescriptor();
			if (introDescriptor != null)
				action.setImageDescriptor(introDescriptor.getImageDescriptor());

			return action;
        }
    };

    public static final ActionFactory COPY = new ActionFactory("copy", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_COPY) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_copy);
            action.setToolTipText(WorkbenchMessages.Workbench_copyToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
            return action;
        }
    };

    public static final ActionFactory CUT = new ActionFactory("cut", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_CUT) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_cut);
            action.setToolTipText(WorkbenchMessages.Workbench_cutToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
            return action;
        }
    };

    public static final ActionFactory DELETE = new ActionFactory("delete", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_DELETE) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_delete);
            action.setToolTipText(WorkbenchMessages.Workbench_deleteToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action.enableAccelerator(false);
            window.getWorkbench().getHelpSystem().setHelp(action,
                    IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
            action
                    .setDisabledImageDescriptor(sharedImages
                            .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
            return action;
        }
    };

    public static final ActionFactory EDIT_ACTION_SETS = new ActionFactory(
            "editActionSets", IWorkbenchCommandConstants.WINDOW_CUSTOMIZE_PERSPECTIVE) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.EditActionSetsAction_text);
            action.setToolTipText(WorkbenchMessages.EditActionSetsAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.EDIT_ACTION_SETS_ACTION);
            
            return action;
        }
    };

    public static final ActionFactory EXPORT = new ActionFactory("export", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_EXPORT) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }

			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ExportResourcesAction_fileMenuText);
            action.setToolTipText(WorkbenchMessages.ExportResourcesAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.EXPORT_ACTION);
            action.setImageDescriptor(WorkbenchImages
                    .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
            return action;
        }
        
    };

    public static final ActionFactory FIND = new ActionFactory("find", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_findReplace);
            action.setToolTipText(WorkbenchMessages.Workbench_findReplaceToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory FORWARD = new ActionFactory("forward", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_FORWARD) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_forward);
            action.setToolTipText(WorkbenchMessages.Workbench_forwardToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory FORWARD_HISTORY = new ActionFactory(
            "forwardHistory", IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new NavigationHistoryAction(window, true);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory GO_INTO = new ActionFactory("goInto", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_GO_INTO) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_goInto);
            action.setToolTipText(WorkbenchMessages.Workbench_goIntoToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory IMPORT = new ActionFactory("import", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_IMPORT) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ImportResourcesAction_text);
            action.setToolTipText(WorkbenchMessages.ImportResourcesAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.IMPORT_ACTION);
            action.setImageDescriptor(WorkbenchImages
                    .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
            return action;

        }
    };

	public static final ActionFactory LOCK_TOOL_BAR = new ActionFactory("lockToolBar", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_LOCK_TOOLBAR) {
        
		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setToolTipText(WorkbenchMessages.LockToolBarAction_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.LOCK_TOOLBAR_ACTION);
			return action;
		}
    };

    public static final ActionFactory MAXIMIZE = new ActionFactory("maximize", //$NON-NLS-1$
    		IWorkbenchCommandConstants.WINDOW_MAXIMIZE_ACTIVE_VIEW_OR_EDITOR) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setToolTipText(WorkbenchMessages.MaximizePartAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.MAXIMIZE_PART_ACTION);
            
            return action;
        }
    };

    public static final ActionFactory MINIMIZE = new ActionFactory("minimize", //$NON-NLS-1$
    		IWorkbenchCommandConstants.WINDOW_MINIMIZE_ACTIVE_VIEW_OR_EDITOR) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
			action.setId(getId());
			action.setToolTipText(WorkbenchMessages.MinimizePartAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.MINIMIZE_PART_ACTION);
			return action;
        }
    };

    public static final ActionFactory MOVE = new ActionFactory("move", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_MOVE) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_move);
            action.setToolTipText(WorkbenchMessages.Workbench_moveToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory NEW = new ActionFactory("new", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_NEW) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            ISharedImages images = window.getWorkbench().getSharedImages();
            action.setImageDescriptor(images
                    .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
            action.setDisabledImageDescriptor(images
                    .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
            action.setText(WorkbenchMessages.NewWizardAction_text);
            action.setToolTipText(WorkbenchMessages.NewWizardAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.NEW_ACTION);
            return action;
        }
    };

    public static final ActionFactory NEW_WIZARD_DROP_DOWN = new ActionFactory(
            "newWizardDropDown") { //$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new NewWizardDropDownAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory NEXT = new ActionFactory("next", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_NEXT) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_next);
            action.setToolTipText(WorkbenchMessages.Workbench_nextToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory NEXT_EDITOR = new ActionFactory(
            "nextEditor", IWorkbenchCommandConstants.WINDOW_NEXT_EDITOR) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new WorkbenchCommandAction(
					getCommandId(), window);

			action.setId(getId());
			action.setText(WorkbenchMessages.CycleEditorAction_next_text);
			action.setToolTipText(WorkbenchMessages.CycleEditorAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_EDITOR_FORWARD_ACTION);
            
			return action;
		}
    };

    public static final ActionFactory NEXT_PART = new ActionFactory("nextPart", //$NON-NLS-1$
    		IWorkbenchCommandConstants.WINDOW_NEXT_VIEW) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePartAction_next_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PART_FORWARD_ACTION);
            return action;
        }
    };

    public static final ActionFactory NEXT_PERSPECTIVE = new ActionFactory(
            "nextPerspective", IWorkbenchCommandConstants.WINDOW_NEXT_PERSPECTIVE) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePerspectiveAction_next_text);
            action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_next_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_FORWARD_ACTION);
            return action;
        }
    };

    public static final ActionFactory OPEN_NEW_WINDOW = new ActionFactory(
            "openNewWindow", IWorkbenchCommandConstants.WINDOW_NEW_WINDOW) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.OpenInNewWindowAction_text);
            action.setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
            		IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
            return action;
        }
        
    };

    public static final ActionFactory PASTE = new ActionFactory("paste", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_PASTE) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_paste);
            action.setToolTipText(WorkbenchMessages.Workbench_pasteToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
            return action;
        }
    };

    public static final ActionFactory PREFERENCES = new ActionFactory(
            "preferences", IWorkbenchCommandConstants.WINDOW_PREFERENCES) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new OpenPreferencesAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory PREVIOUS = new ActionFactory("previous", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_PREVIOUS) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_previous);
            action.setToolTipText(WorkbenchMessages.Workbench_previousToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory PREVIOUS_EDITOR = new ActionFactory(
            "previousEditor", IWorkbenchCommandConstants.WINDOW_PREVIOUS_EDITOR) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			IWorkbenchAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CycleEditorAction_prev_text);
            action.setToolTipText(WorkbenchMessages.CycleEditorAction_prev_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_EDITOR_BACKWARD_ACTION);

            return action;
        }
    };

    public static final ActionFactory PREVIOUS_PART = new ActionFactory(
            "previousPart", IWorkbenchCommandConstants.WINDOW_PREVIOUS_VIEW) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
			action.setText(WorkbenchMessages.CyclePartAction_prev_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_prev_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PART_BACKWARD_ACTION);
            return action;
        }
    };

    public static final ActionFactory PREVIOUS_PERSPECTIVE = new ActionFactory(
            "previousPerspective", IWorkbenchCommandConstants.WINDOW_PREVIOUS_PERSPECTIVE) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePerspectiveAction_prev_text);
            action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_prev_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_BACKWARD_ACTION);
            return action;
        }
    };

    public static final ActionFactory PRINT = new ActionFactory("print", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_PRINT) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_print);
            action.setToolTipText(WorkbenchMessages.Workbench_printToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action
                    .setImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
            action
                    .setDisabledImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
            return action;
        }
    };

    public static final ActionFactory PROPERTIES = new ActionFactory(
            "properties", IWorkbenchCommandConstants.FILE_PROPERTIES) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_properties);
            action.setToolTipText(WorkbenchMessages.Workbench_propertiesToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory QUIT = new ActionFactory("quit", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_EXIT) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.Exit_text);
            action.setToolTipText(WorkbenchMessages.Exit_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.QUIT_ACTION);
            return action;
        }
    };

    public static final ActionFactory REDO = new ActionFactory("redo", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_REDO) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_redo);
            action.setToolTipText(WorkbenchMessages.Workbench_redoToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_REDO_DISABLED));
            return action;
        }
    };

    public static final ActionFactory REFRESH = new ActionFactory("refresh", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_REFRESH) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_refresh);
            action.setToolTipText(WorkbenchMessages.Workbench_refreshToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory RENAME = new ActionFactory("rename", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_RENAME) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_rename);
            action.setToolTipText(WorkbenchMessages.Workbench_renameToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory RESET_PERSPECTIVE = new ActionFactory(
            "resetPerspective", IWorkbenchCommandConstants.WINDOW_RESET_PERSPECTIVE) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);

			action.setId(getId());
			action.setText(WorkbenchMessages.ResetPerspective_text);
			action.setToolTipText(WorkbenchMessages.ResetPerspective_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.RESET_PERSPECTIVE_ACTION);
			return action;
        }
    };

    public static final ActionFactory REVERT = new ActionFactory("revert", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_REVERT) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_revert);
            action.setToolTipText(WorkbenchMessages.Workbench_revertToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory SAVE = new ActionFactory("save", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_SAVE) {
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setText(WorkbenchMessages.SaveAction_text);
			action.setToolTipText(WorkbenchMessages.SaveAction_toolTip);
            action.setId(getId());
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.SAVE_ACTION);
            return action;
        }
    };

    public static final ActionFactory SAVE_ALL = new ActionFactory("saveAll", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_SAVE_ALL) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			IWorkbenchAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setText(WorkbenchMessages.SaveAll_text);
			action.setToolTipText(WorkbenchMessages.SaveAll_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.SAVE_ALL_ACTION);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory SAVE_AS = new ActionFactory("saveAs", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_SAVE_AS) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			IWorkbenchAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setText(WorkbenchMessages.SaveAs_text);
			action.setToolTipText(WorkbenchMessages.SaveAs_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.SAVE_AS_ACTION);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory SAVE_PERSPECTIVE = new ActionFactory(
            "savePerspective", IWorkbenchCommandConstants.WINDOW_SAVE_PERSPECTIVE_AS) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);

			action.setId(getId());
			action.setText(WorkbenchMessages.SavePerspective_text);
			action.setToolTipText(WorkbenchMessages.SavePerspective_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_ACTION);
			return action;
		}
    };

    public static final ActionFactory SELECT_ALL = new ActionFactory(
            "selectAll", IWorkbenchCommandConstants.EDIT_SELECT_ALL) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_selectAll);
            action.setToolTipText(WorkbenchMessages.Workbench_selectAllToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory SHOW_EDITOR = new ActionFactory(
            "showEditor") {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new ToggleEditorsVisibilityAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory SHOW_OPEN_EDITORS = new ActionFactory(
            "showOpenEditors") {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action = new WorkbenchCommandAction("org.eclipse.ui.window.switchToEditor", window); //$NON-NLS-1$
            action.setId(getId());
            action.setText(WorkbenchMessages.WorkbenchEditorsAction_label);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.WORKBENCH_EDITORS_ACTION);
            return action;
        }
    };

    public static final ActionFactory SHOW_WORKBOOK_EDITORS = new ActionFactory(
            "showWorkBookEditors") {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            
            WorkbenchCommandAction action = new WorkbenchCommandAction("org.eclipse.ui.window.openEditorDropDown", window); //$NON-NLS-1$
            action.setId(getId());
            action.setText(WorkbenchMessages.WorkbookEditorsAction_label);
            
            return action;
        }
    };

	public static final ActionFactory SHOW_QUICK_ACCESS = new ActionFactory(
			"showQuickAccess") { //$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			WorkbenchCommandAction action = new WorkbenchCommandAction("org.eclipse.ui.window.quickAccess", window); //$NON-NLS-1$
			action.setId(getId());
			action.setText(WorkbenchMessages.QuickAccessAction_text);
			action.setToolTipText(WorkbenchMessages.QuickAccessAction_toolTip);
			return action;
		}

	};

    public static final ActionFactory SHOW_PART_PANE_MENU = new ActionFactory(
            "showPartPaneMenu") {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action=new WorkbenchCommandAction("org.eclipse.ui.window.showSystemMenu",window); //$NON-NLS-1$
            action.setId(getId());
            action.setText(WorkbenchMessages.ShowPartPaneMenuAction_text);
            action.setToolTipText(WorkbenchMessages.ShowPartPaneMenuAction_toolTip);
            return action;
        }
    };

    public static final ActionFactory SHOW_VIEW_MENU = new ActionFactory(
            "showViewMenu", IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ShowViewMenuAction_text);
            action.setToolTipText(WorkbenchMessages.ShowViewMenuAction_toolTip);
            return action;
        }
    };

    public static final ActionFactory UNDO = new ActionFactory("undo", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_UNDO) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_undo);
            action.setToolTipText(WorkbenchMessages.Workbench_undoToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
            return action;
        }
    };

    public static final ActionFactory UP = new ActionFactory("up", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_UP) {
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_up);
            action.setToolTipText(WorkbenchMessages.Workbench_upToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    public static final ActionFactory HELP_CONTENTS = new ActionFactory(
            "helpContents", IWorkbenchCommandConstants.HELP_HELP_CONTENTS) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new HelpContentsAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory HELP_SEARCH = new ActionFactory(
            "helpSearch", IWorkbenchCommandConstants.HELP_HELP_SEARCH) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new HelpSearchAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory DYNAMIC_HELP = new ActionFactory(
            "dynamicHelp", IWorkbenchCommandConstants.HELP_DYNAMIC_HELP) {//$NON-NLS-1$
        
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new DynamicHelpAction(window);
            action.setId(getId());
            return action;
        }
    };

    public static final ActionFactory OPEN_PERSPECTIVE_DIALOG = new ActionFactory(
            "openPerspectiveDialog", IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
			action.setId(getId());
	        action.setText(WorkbenchMessages.OpenPerspectiveDialogAction_text);
	        action.setToolTipText(WorkbenchMessages.OpenPerspectiveDialogAction_tooltip);
	        action.setImageDescriptor(WorkbenchImages.getImageDescriptor(
	              IWorkbenchGraphicConstants.IMG_ETOOL_NEW_PAGE));

			return action;
        }
    };

    public static final ActionFactory NEW_EDITOR = new ActionFactory(
            "newEditor", IWorkbenchCommandConstants.WINDOW_NEW_EDITOR) {//$NON-NLS-1$
       
        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }

			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.NewEditorAction_text);
			action.setToolTipText(WorkbenchMessages.NewEditorAction_tooltip);

			return action;
        }
    };

	public static final ActionFactory TOGGLE_COOLBAR = new ActionFactory(
			"toggleCoolbar") { //$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					"org.eclipse.ui.ToggleCoolbarAction", window); //$NON-NLS-1$
			action.setId(getId());
			action.setText(WorkbenchMessages.ToggleCoolbarVisibilityAction_hide_text);
			action.setToolTipText(WorkbenchMessages.ToggleCoolbarVisibilityAction_toolTip);
			return action;
		}
	};
    
    public static void linkCycleActionPair(IWorkbenchAction next,
            IWorkbenchAction previous) {
    }

    private final String actionId;
    
    private final String commandId;

    protected ActionFactory(String actionId) {
    	this(actionId, null);
    }

    protected ActionFactory(String actionId, String commandId) {
    	this.actionId = actionId;
    	this.commandId = commandId;
    }

    public abstract IWorkbenchAction create(IWorkbenchWindow window);

    public String getId() {
        return actionId;
    }

    public String getCommandId() {
    	return commandId;
    }
}
