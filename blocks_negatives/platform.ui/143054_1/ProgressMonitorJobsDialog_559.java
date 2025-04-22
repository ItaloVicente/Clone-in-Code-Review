			        .getProgressViewerContentProvider(viewer, true, false));
            viewer.setLabelProvider(new ProgressLabelProvider());
            viewer.setInput(this);
            GridData viewerData = new GridData(GridData.FILL_BOTH);
            viewer.getControl().setLayoutData(viewerData);
            GridData viewerCompositeData = (GridData) viewerComposite.getLayoutData();
            viewerCompositeData.heightHint = convertHeightInCharsToPixels(10);
            viewerComposite.layout(true);
            viewer.getControl().setVisible(true);
            viewerHeight = viewerComposite.computeTrim(0, 0, 0, viewerCompositeData.heightHint).height;
            detailsButton.setText(ProgressMessages.ProgressMonitorJobsDialog_HideTitle);
            shell.setSize(shellSize.x, shellSize.y + viewerHeight);
        }
    }

    @Override
