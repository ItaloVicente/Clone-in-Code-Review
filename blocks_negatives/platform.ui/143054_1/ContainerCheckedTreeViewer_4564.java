        super.setExpanded(item, expand);
        if (expand && item instanceof TreeItem) {
            initializeItem((TreeItem) item);
        }
    }

