		} else {
			textNode.setData(data);
		}
	}

	public void save(Writer writer) throws IOException {
		try (DOMWriter out = new DOMWriter(writer)) {
			out.print(element);
		}
