            				canPaste = true;
            				break;
            			}
            		}

            		clipboard.dispose();
            	}

                setEnabled(canPaste);
                return;
            }
            if (pasteAction != null) {
                setEnabled(pasteAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    private class SelectAllActionHandler extends Action {
        protected SelectAllActionHandler() {
            super(IDEWorkbenchMessages.TextAction_selectAll);
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
