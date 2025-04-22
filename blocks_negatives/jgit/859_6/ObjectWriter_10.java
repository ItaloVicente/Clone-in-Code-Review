			if (id == null && deflateStream != null) {
				try {
					deflateStream.close();
				} finally {
					t.delete();
				}
			}
			if (def != null) {
				def.end();
			}
