			List<String> list;
			if (ret.containsKey(hdr.getName())) {
				list = ret.get(hdr.getName());
			} else {
				list = new LinkedList<>();
				ret.put(hdr.getName()
			}
			for (HeaderElement hdrElem : hdr.getElements()) {
