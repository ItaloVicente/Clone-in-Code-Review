	private Comparator<Ref> tagTieBreaker() {
		return new Comparator<Ref>() {
			public int compare(Ref o1
				try {
					return tagDate(o2).compareTo(tagDate(o1));
				} catch (IOException e) {
					return 0;
				}
			}

			private Date tagDate(Ref tag) throws IOException {
				RevTag t = w.parseTag(tag.getObjectId());
				w.parseBody(t);
				return t.getTaggerIdent().getWhen();
			}
		};
	}

