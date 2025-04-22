        animationItem.addMouseListener(new MouseListener() {
            @Override
			public void mouseDoubleClick(MouseEvent arg0) {
                ProgressManagerUtil.openProgressView();
            }

            @Override
			public void mouseDown(MouseEvent arg0) {
            }

            @Override
			public void mouseUp(MouseEvent arg0) {
            }
        });
        animationItem.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                animationManager.removeItem(AnimationItem.this);
            }
        });
