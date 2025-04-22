			RevCommit result = rw.next();
			if (result != null) {
				rw.parseBody(result);
				return result;
			} else {
				return null;
			}
		} finally {
			rw.release();
		}
