			Collections.sort(changes, Collections.reverseOrder());
			set(new LinkedHashSet<>(changes));
		}

		@Override
		protected void done() {
			listOp = null;
