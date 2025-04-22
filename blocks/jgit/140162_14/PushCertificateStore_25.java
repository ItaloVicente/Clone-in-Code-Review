			@Override
			public boolean hasNext() {
				try {
					if (next == null) {
						if (rw == null) {
							return false;
						}
