                Control[] children = composite.getChildren();
                if (children.length == 0)
                    return;

                children[0].setLocation(2, 2);
            }
        });
    }

    /**
     * Dispose the frame
     */
    private void dispose() {
        if (canvas != null && !canvas.isDisposed())
            canvas.dispose();
    }

    /**
     * @return The border canvas
     */
    public Composite getComposite() {
    	return canvas;
    }
