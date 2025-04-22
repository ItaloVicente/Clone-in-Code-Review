			List<String> list = ret.get(hdr.getName());
			if (list == null) {
				list = new LinkedList<>();
				ret.put(hdr.getName()
			}
			for (HeaderElement hdrElem : hdr.getElements()) {
