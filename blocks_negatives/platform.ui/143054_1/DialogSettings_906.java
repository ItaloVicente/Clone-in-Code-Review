        Document document = null;
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            document = parser.parse(new InputSource(r));

            Node root = document.getFirstChild();
            while (root.getNodeType() == Node.COMMENT_NODE) {
                document.removeChild(root);
                root = document.getFirstChild();
            }
            load(document, (Element) root);
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        } catch (SAXException e) {
        }
    }

    @Override
