        for (int i = 0; i < items.length; i++) {
            if (items[i].isSeparator()) {
                separatorFound = true;
            }
            if ((separatorFound) && (isChildVisible(items[i]))
                    && (!items[i].isGroupMarker()) && (!items[i].isSeparator())) {
                numRows++;
                separatorFound = false;
            }
        }
