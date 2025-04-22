        NamedNodeMap nodeMap = element.getAttributes();
        int size = nodeMap.getLength();
        for (int i = 0; i < size; i++) {
            Attr attr = (Attr) nodeMap.item(i);
            putString(attr.getName(), attr.getValue());
        }

        NodeList nodes = element.getChildNodes();
        size = nodes.getLength();
        boolean needToCopyText = copyText;
        for (int i = 0; i < size; i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                XMLMemento child = (XMLMemento) createChild(node.getNodeName());
                child.putElement((Element) node, true);
            } else if (node instanceof Text && needToCopyText) {
                putTextData(((Text) node).getData());
                needToCopyText = false;
            }
        }
    }
