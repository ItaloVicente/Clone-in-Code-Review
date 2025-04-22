		@Override
		public File gitPrefix() {
			return gitDir;
		}

		@Override
		public File resolve(final File dir
			if (dir == null && name.equals("/etc"))
				return etcDir;
			if (dir == gitDir && name.equals("etc"))
				return new File(dir
			if (dir == null
					&& name.equalsIgnoreCase(otherDir.getAbsolutePath()))
				return otherDir;

			fail();
			return null;
		}

