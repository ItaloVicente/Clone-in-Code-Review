
        if (isDirty() || force) {

            if (statusLineExist()) {
                statusLine.setRedraw(false);


                Control ws[] = statusLine.getChildren();
                for (Control w : ws) {
                    Object data = w.getData();
                    if (data instanceof IContributionItem) {
                        w.dispose();
                    }
                }

                int oldChildCount = statusLine.getChildren().length;
                IContributionItem[] items = getItems();
                for (IContributionItem ci : items) {
                    if (ci.isVisible()) {
                        ci.fill(statusLine);
                        Control[] newChildren = statusLine.getChildren();
                        for (int j = oldChildCount; j < newChildren.length; j++) {
                            newChildren[j].setData(ci);
                        }
                        oldChildCount = newChildren.length;
                    }
                }

                setDirty(false);

                statusLine.requestLayout();
                statusLine.setRedraw(true);
            }
        }
    }
