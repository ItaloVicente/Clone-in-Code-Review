				if (vr != null) {
					Collection<String> ids = new LinkedList<String>();
					Iterator<RowWithDocs> itr = vr.iterator();
					while (itr.hasNext()) {
						ids.add(itr.next().getId());
					}
					crv.set(vr, asyncGetBulk(ids), status);
				} else {
					crv.set(null, null, status);
