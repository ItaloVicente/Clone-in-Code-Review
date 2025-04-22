			runOther(new SelectionEvent(event));
		}
	};

	public PerspectiveMenu(IWorkbenchWindow window, String id) {
		super(id);
		this.window = window;
		reg = window.getWorkbench().getPerspectiveRegistry();
		openOtherAction.setActionDefinitionId(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE);
	}

	@Override
