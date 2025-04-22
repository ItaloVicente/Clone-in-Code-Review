        dialog.showClosedProjects(false);
        dialog.open();
        Object[] result = dialog.getResult();
        if (result != null && result.length == 1) {
            return (IPath) result[0];
        }
        return null;
    }

    /**
     * The <code>WizardDataTransfer</code> implementation of this
     * <code>IOverwriteQuery</code> method asks the user whether the existing
     * resource at the given path should be overwritten.
     *
     * @param pathString
     * @return the user's reply: one of <code>"YES"</code>, <code>"NO"</code>, <code>"ALL"</code>,
     *   or <code>"CANCEL"</code>
     */
    @Override
