

		String stateToRestore = persp.getPersistedState().get(E4Workbench.PERSPECTIVE_RESET_STATE);
		if (stateToRestore != null && !stateToRestore.isEmpty()) {
			MPerspective state = (MPerspective) ModelUtils
					.base64StringToModelElement(stateToRestore);
			state.getPersistedState().put(E4Workbench.PERSPECTIVE_RESET_STATE, stateToRestore);

			persp.setToBeRendered(false);

			EcoreUtil.replace((EObject) persp, (EObject) state);

			EPartService ps = window.getContext().get(EPartService.class);
			ps.switchPerspective(state); // no null-check, because we want to fail early
		}
