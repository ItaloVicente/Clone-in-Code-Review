                IProject[] projects = ((IWorkspace) element).getRoot()
                        .getProjects();
                return projects == null ? new Object[0] : projects;
            }
        };
    }

    /**
     * Get the defualt widget height for the supplied control.
     * @return int
     * @param control - the control being queried about fonts
     * @param lines - the number of lines to be shown on the table.
     */
    private static int getDefaultFontHeight(Control control, int lines) {
        FontData[] viewerFontData = control.getFont().getFontData();
        int fontHeight = 10;

        if (viewerFontData.length > 0) {
