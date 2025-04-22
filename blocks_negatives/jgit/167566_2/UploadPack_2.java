	private void addTagChain(
			RevObject o, PackWriter pw) throws IOException {
		while (Constants.OBJ_TAG == o.getType()) {
			RevTag t = (RevTag) o;
			o = t.getObject();
			if (o.getType() == Constants.OBJ_TAG && !pw.willInclude(o.getId())) {
				walk.parseBody(o);
				pw.addObject(o);
			}
		}
	}

