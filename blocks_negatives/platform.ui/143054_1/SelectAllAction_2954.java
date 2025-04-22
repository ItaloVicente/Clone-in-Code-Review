        Viewer viewer = getView().getViewer();
        Control control = viewer.getControl();
        if (control instanceof Table) {
            ((Table) control).selectAll();
            viewer.setSelection(viewer.getSelection(), false);
        }
    }
