        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream, StandardCharsets.UTF_8));
        load(reader);
        reader.close();
    }

    private void load(Document document, Element root) {
        name = root.getAttribute(TAG_NAME);
        NodeList l = root.getElementsByTagName(TAG_ITEM);
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (root == n.getParentNode()) {
                String key = ((Element) l.item(i)).getAttribute(TAG_KEY);
                String value = ((Element) l.item(i)).getAttribute(TAG_VALUE);
                items.put(key, value);
            }
        }
        l = root.getElementsByTagName(TAG_LIST);
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (root == n.getParentNode()) {
                Element child = (Element) l.item(i);
                String key = child.getAttribute(TAG_KEY);
                NodeList list = child.getElementsByTagName(TAG_ITEM);
                List<String> valueList = new ArrayList<>();
                for (int j = 0; j < list.getLength(); j++) {
                    Element node = (Element) list.item(j);
                    if (child == node.getParentNode()) {
                        valueList.add(node.getAttribute(TAG_VALUE));
                    }
                }
                String[] value = new String[valueList.size()];
                valueList.toArray(value);
                arrayItems.put(key, value);
            }
        }
        l = root.getElementsByTagName(TAG_SECTION);
        for (int i = 0; i < l.getLength(); i++) {
            Node n = l.item(i);
            if (root == n.getParentNode()) {
                s.load(document, (Element) n);
                addSection(s);
            }
        }
    }

    @Override
