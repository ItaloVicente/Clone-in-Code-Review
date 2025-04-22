        frameList.addPropertyChangeListener(new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
                TreeViewerFrameSource.this.handlePropertyChange(event);
            }
        });
