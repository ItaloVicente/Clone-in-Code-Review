        animationItem.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                AnimationManager.getInstance().removeItem(AnimationItem.this);
            }
        });
