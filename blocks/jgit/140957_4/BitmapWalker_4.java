		@Override
		public void postWalk(RevWalk walk
				ProgressMonitor pm) throws IOException {
			ObjectWalk objectWalk = (ObjectWalk) walk;
			RevObject ro;
			while ((ro = objectWalk.nextObject()) != null) {
				bitmapResult.addObject(ro
				pm.update(1);
			}
		}
	};
