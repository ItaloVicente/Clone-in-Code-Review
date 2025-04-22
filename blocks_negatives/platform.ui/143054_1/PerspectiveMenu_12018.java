            runOther(new SelectionEvent(event));
        }
    };


    /**
     * Constructs a new instance of <code>PerspectiveMenu</code>.
     *
     * @param window the window containing this menu
     * @param id the menu id
     */
    public PerspectiveMenu(IWorkbenchWindow window, String id) {
        super(id);
        this.window = window;
        reg = window.getWorkbench().getPerspectiveRegistry();
		openOtherAction
				.setActionDefinitionId(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE);
    }

    @Override
