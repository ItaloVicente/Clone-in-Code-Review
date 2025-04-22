            }
        }
        return false;
    }

    /**
     * Apply the dialog font to the control and store
     * it for later so that it can be used for a later
     * update.
     * @param control
     */
    private void myApplyDialogFont(Control control) {
        control.setFont(JFaceResources.getDialogFont());
        dialogFontWidgets.add(control);
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    @Override
