        descImageCanvas.addDisposeListener(new DisposeListener() {

            @Override
			public void widgetDisposed(DisposeEvent e) {
                for (Iterator i = imageTable.values().iterator(); i.hasNext();) {
                    ((Image) i.next()).dispose();
                }
                imageTable.clear();
            }
        });
