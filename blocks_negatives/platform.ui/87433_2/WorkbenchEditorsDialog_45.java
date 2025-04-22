        editorsTable.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                for (Iterator images = imageCache.values().iterator(); images
                        .hasNext();) {
                    Image i = (Image) images.next();
                    i.dispose();
                }
                for (Iterator images = disabledImageCache.values().iterator(); images
                        .hasNext();) {
                    Image i = (Image) images.next();
                    i.dispose();
                }
            }
        });
