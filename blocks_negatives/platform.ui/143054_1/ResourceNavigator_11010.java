                    if (element != null) {
                        elements.add(element);
                    }
                }
                viewer.setExpandedElements(elements.toArray());
            }
            childMem = memento.getChild(TAG_SELECTION);
            if (childMem != null) {
                ArrayList list = new ArrayList();
