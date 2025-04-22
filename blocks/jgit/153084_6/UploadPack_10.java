			super.writePack(pw);
		}

		@Override
		protected DepthWalk.RevWalk applyDepth(PackWriter pw) {
			DepthWalk.RevWalk dw = super.applyDepth(pw);
			dw.setDeepenNots(deepenNots);
			return dw;
