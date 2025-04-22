            }
            internalCollectGrayed(result, item);
        }
    }

    /**
     * Sets the checked state of all items to correspond to the given set of checked elements.
     *
     * @param checkedElements the set (element type: <code>Object</code>) of elements which are checked
     * @param widget the widget
     */
    private void internalSetChecked(CustomHashtable checkedElements,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (Item child : items) {
            TreeItem item = (TreeItem) child;
            Object data = item.getData();
            if (data != null) {
                boolean checked = checkedElements.containsKey(data);
                if (checked != item.getChecked()) {
                    item.setChecked(checked);
                }
            }
            internalSetChecked(checkedElements, item);
        }
    }

    /**
     * Sets the grayed state of all items to correspond to the given set of grayed elements.
     *
     * @param grayedElements the set (element type: <code>Object</code>) of elements which are grayed
     * @param widget the widget
     */
    private void internalSetGrayed(CustomHashtable grayedElements, Widget widget) {
        Item[] items = getChildren(widget);
        for (Item child : items) {
            TreeItem item = (TreeItem) child;
            Object data = item.getData();
            if (data != null) {
                boolean grayed = grayedElements.containsKey(data);
                if (grayed != item.getGrayed()) {
                    item.setGrayed(grayed);
                }
            }
            internalSetGrayed(grayedElements, item);
        }
    }

    @Override
