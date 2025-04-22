        if (menu != null && !menu.isDisposed() && menu.getParentItem() != null) {
        	if (IAction.TEXT.equals(property)) {
                String text = getOverrides().getText(this);

                if (text == null) {
    				text = getMenuText();
    			}

                if (text != null) {
                    ExternalActionManager.ICallback callback = ExternalActionManager
                            .getInstance().getCallback();

                    if (callback != null) {
                        int index = text.indexOf('&');

                        if (index >= 0 && index < text.length() - 1) {
                            char character = Character.toUpperCase(text
                                    .charAt(index + 1));

                            if (callback.isAcceleratorInUse(SWT.ALT | character) && isTopLevelMenu()) {
                                if (index == 0) {
    								text = text.substring(1);
    							} else {
    								text = text.substring(0, index)
                                            + text.substring(index + 1);
    							}
                            }
                        }
                    }

                    menu.getParentItem().setText(text);
                }
        	} else if (IAction.IMAGE.equals(property) && image != null) {
    			LocalResourceManager localManager = new LocalResourceManager(JFaceResources
    					.getResources());
    			menu.getParentItem().setImage(localManager.createImage(image));
    			disposeOldImages();
    			imageManager = localManager;
        	}
        }
    }
