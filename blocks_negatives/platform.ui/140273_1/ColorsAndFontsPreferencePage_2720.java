                    GC gc = new GC(image);
                    gc.setBackground(tree.getViewer().getControl()
                            .getBackground());
                    gc.setForeground(tree.getViewer().getControl()
                            .getBackground());
                    gc.drawRectangle(0, 0, imageSize - 1, imageSize - 1);
