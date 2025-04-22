				IgnoreNode r = new IgnoreNode();
				InputStream in = entry.openInputStream();
				try {
					r.parse(in);
				} finally {
					in.close();
				}
				return r.getRules().isEmpty() ? null : r;
			} catch (AccessControlException ignored) {
				return null;
