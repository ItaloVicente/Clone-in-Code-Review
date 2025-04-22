
	private static class MockedObjectUseAsIs extends ObjectReader implements ObjectReuseAsIs {
		private int cachedPacksAndUpdateInvocations = 0;
		private boolean throwStaleFileHandle = true;

		public void setThrowStaleFileHandle(boolean throwStaleFileHandle) {
			this.throwStaleFileHandle = throwStaleFileHandle;
		}

		@Override
		public ObjectToPack newObjectToPack(AnyObjectId objectId
			return null;
		}

		@Override
		public void selectObjectRepresentation(PackWriter packer
						ProgressMonitor monitor
				throws IOException
		}

		@Override
		public void writeObjects(PackOutputStream out
		}

		@Override
		public void copyObjectAsIs(PackOutputStream out
									boolean validate)
				throws IOException
		}

		@Override
		public void copyPackAsIs(PackOutputStream out
			if (throwStaleFileHandle) {
				throw new IOException("Stale file handle");
			}
		}

		@Override
		public Collection <CachedPack> getCachedPacksAndUpdate(BitmapIndex.BitmapBuilder needBitmap)
				throws IOException {
			cachedPacksAndUpdateInvocations++;
			return null;
		}

		@Override
		public ObjectReader newReader() {
			return null;
		}

		@Override
		public Collection <ObjectId> resolve(AbbreviatedObjectId id) throws IOException {
			return null;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
			return null;
		}

		@Override
		public Set <ObjectId> getShallowCommits() throws IOException {
			return null;
		}

		@Override
		public void close() {
		}
	}
