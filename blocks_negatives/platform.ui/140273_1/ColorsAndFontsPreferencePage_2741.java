	        previewControl = (Composite) previewMap.get(category);
	        if (previewControl == null) {
                try {
                    IThemePreview preview = getThemePreview(category);
                    if (preview != null) {
                        previewControl = new Composite(previewComposite, SWT.NONE);
                        previewControl.setLayout(new FillLayout());
                        ITheme theme = getCascadingTheme();
                        preview.createControl(previewControl, theme);
                        previewSet.add(preview);
                        previewMap.put(category, previewControl);
                    }
                } catch (CoreException e) {
                    previewControl = new Composite(previewComposite, SWT.NONE);
                    previewControl.setLayout(new FillLayout());
                    myApplyDialogFont(previewControl);
                    Text error = new Text(previewControl, SWT.WRAP | SWT.READ_ONLY);
                    WorkbenchPlugin.log(RESOURCE_BUNDLE.getString("errorCreatePreviewLog"), //$NON-NLS-1$
                    		StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
                }
	        }
		}

        	if (element instanceof ColorDefinition)
        		previewControl = defaultColorPreview;
        	else if (element instanceof FontDefinition)
        		previewControl = defaultFontPreview;
        	else
        		previewControl = defaultNoPreview;
        }

        stackLayout.topControl = previewControl;
        previewComposite.layout();
        updateControls();
	}

    /**
