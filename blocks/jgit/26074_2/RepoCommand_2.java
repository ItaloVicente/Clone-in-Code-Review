	public interface RemoteReader {
		public ObjectId sha1(String uri) throws GitAPIException;
	}

	public static class DefaultRemoteReader implements RemoteReader {
		public ObjectId sha1(String uri) throws GitAPIException {
			Collection<Ref> refs = Git
					.lsRemoteRepository()
					.setRemote(uri)
					.call();
			for (Ref ref : refs) {
				if (Constants.HEAD.equals(ref.getName()))
					return ref.getObjectId();
			}
			return null;
		}
	}

