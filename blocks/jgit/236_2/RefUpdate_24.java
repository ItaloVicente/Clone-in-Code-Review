	protected abstract RefDatabase getRefDatabase();

	protected abstract Repository getRepository();

	protected abstract boolean tryLock(boolean deref) throws IOException;

	protected abstract void unlock();

	protected abstract Result doUpdate(Result desiredResult) throws IOException;

	protected abstract Result doDelete(Result desiredResult) throws IOException;

	protected abstract Result doLink(String target) throws IOException;
