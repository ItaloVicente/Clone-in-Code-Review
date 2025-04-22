        Document document;
        try {
            document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            Element element = document.createElement(type);
            document.appendChild(element);
            return new XMLMemento(document, element);
        } catch (ParserConfigurationException e) {
