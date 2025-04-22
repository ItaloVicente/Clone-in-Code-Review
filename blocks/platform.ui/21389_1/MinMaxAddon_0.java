		private MWindow getWindow(MToolControl tc) {
			MUIElement curParent = tc.getParent();
			while (curParent != null && !(curParent instanceof MWindow))
				curParent = curParent.getParent();
			return (MWindow) curParent;
		}

