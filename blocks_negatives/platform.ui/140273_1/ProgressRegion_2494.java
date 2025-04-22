        	gd.widthHint = viewerSizeHints.x;
        	gd.heightHint = viewerSizeHints.y;
        } else {
        	gd.widthHint = viewerSizeHints.y;
        	gd.heightHint = viewerSizeHints.x;
        }
        viewerControl.setLayoutData(gd);

        int widthPreference = AnimationManager.getInstance()
                .getPreferredWidth() + 25;
