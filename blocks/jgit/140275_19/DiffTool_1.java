
		@Override
		public boolean prompt(String toolToLaunchName) {
			try {
				boolean launchCompare = true;
				outw.flush();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(ins));
				String line = null;
				if ((line = br.readLine()) != null) {
						launchCompare = false;
					}
				}
				return launchCompare;
			} catch (IOException e) {
				throw new IllegalStateException("Cannot output text"
			}
