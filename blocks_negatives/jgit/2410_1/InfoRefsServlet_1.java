				@Override
				protected void end() {
				}
			};
			adv.init(walk, ADVERTISED);
			adv.setDerefTags(true);

			Map<String, Ref> refs = db.getAllRefs();
			refs.remove(Constants.HEAD);
			adv.send(refs);
			out.close();
		} finally {
			walk.release();
		}
