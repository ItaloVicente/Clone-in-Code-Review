		} else {
			textNode.setData(data);
		}
	}

	public void save(Writer writer) throws IOException {
		DOMWriter out = new DOMWriter(writer);
		try {
			out.print(element);
		} finally {
			out.close();
		}
