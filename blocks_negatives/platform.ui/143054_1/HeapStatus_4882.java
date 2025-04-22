            clearMark();
        }
    }

    class ShowMaxAction extends Action {
    	ShowMaxAction() {
            super(WorkbenchMessages.ShowMaxAction_text, IAction.AS_CHECK_BOX);
            setEnabled(maxMemKnown);
            setChecked(showMax);
        }

        @Override
