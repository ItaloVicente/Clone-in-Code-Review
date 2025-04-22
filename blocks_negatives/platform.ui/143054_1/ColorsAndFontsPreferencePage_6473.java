                    gc.setBackground(c);

                    int offset = (imageSize - usableImageSize) / 2;
                    gc.drawRectangle(offset, offset, usableImageSize - offset,
                            usableImageSize - offset);
                    gc.fillRectangle(offset + 1, offset + 1, usableImageSize
                            - offset - 1, usableImageSize - offset - 1);
                    gc.dispose();

                    images.put(c, image);
                }
                return image;

            } else if (element instanceof FontDefinition) {
                return workbench.getSharedImages().getImage(
                        IWorkbenchGraphicConstants.IMG_OBJ_FONT);
            } else {
                return workbench.getSharedImages().getImage(
                        IWorkbenchGraphicConstants.IMG_OBJ_THEME_CATEGORY);
            }
        }

        private void ensureImageSize() {
            if (imageSize == -1) {
                imageSize = tree.getViewer().getTree().getItemHeight();
                usableImageSize = Math.max(1, imageSize - 4);
            }
        }

        @Override
