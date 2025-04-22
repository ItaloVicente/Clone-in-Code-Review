        } else {
            textNode.setData(data);
        }
    }

    /**
     * Saves this memento's document current values to the
     * specified writer.
     *
     * @param writer the writer used to save the memento's document
     * @throws IOException if there is a problem serializing the document to the stream.
     */
    public void save(Writer writer) throws IOException {
    	DOMWriter out = new DOMWriter(writer);
        try {
        	out.print(element);
    	} finally {
    		out.close();
    	}
