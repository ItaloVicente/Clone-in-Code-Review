			Map<String
			refs.remove(Constants.HEAD);
			adv.send(refs);
			out.close();
		} finally {
			walk.release();
		}
