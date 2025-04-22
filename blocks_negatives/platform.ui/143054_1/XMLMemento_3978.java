        for (int nX = 0; nX < size; nX++) {
            Node node = nodes.item(nX);
            if (node instanceof Text) {
                return (Text) node;
            }
        }
        return null;
    }

    /**
