        reuseEditors.addSelectionListener(widgetSelectedAdapter(e -> {
		    reuseEditorsThreshold
		            .getLabelControl(editorReuseThresholdGroup).setEnabled(
		                    reuseEditors.getSelection());
		    reuseEditorsThreshold.getTextControl(editorReuseThresholdGroup)
		            .setEnabled(reuseEditors.getSelection());
		}));
