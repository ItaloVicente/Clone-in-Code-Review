        for (int j = 0; j < childItems.length; j++) {
            if (isChildVisible(childItems[j]) && !childItems[j].isSeparator()) {
                visibleChildren = true;
                break;
            }
        }
