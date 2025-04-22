package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.events.RepositoryEvent;
import org.eclipse.jgit.storage.file.ReflogReader;
import org.eclipse.jgit.util.FS;


public abstract class RepositoryDelegator extends Repository {

	private Repository delegate;

	public RepositoryDelegator(final Repository delegate) {
		this.delegate = delegate;
	}

	@Override
	public ListenerList getListenerList() {
		throw new IllegalStateException();
	}

	@Override
	public void fireEvent(RepositoryEvent<?> event) {
		throw new IllegalStateException();
	}

	@Override
	public void create() throws IOException {
		throw new IllegalStateException();
	}

	@Override
	public void create(boolean bare) throws IOException {
		throw new IllegalStateException();
	}

	@Override
	public File getDirectory() {
		return delegate.getDirectory();
	}

	@Override
	public ObjectDatabase getObjectDatabase() {
		return delegate.getObjectDatabase();
	}

	@Override
	public ObjectInserter newObjectInserter() {
		return delegate.newObjectInserter();
	}

	@Override
	public ObjectReader newObjectReader() {
		return delegate.newObjectReader();
	}

	@Override
	public RefDatabase getRefDatabase() {
		return delegate.getRefDatabase();
	}

	@Override
	public GraftsDatabase getGraftsDatabase() {
		return delegate.getGraftsDatabase();
	}

	@Override
	public StoredConfig getConfig() {
		return delegate.getConfig();
	}

	@Override
	public FS getFS() {
		return delegate.getFS();
	}

	@Override
	public boolean hasObject(AnyObjectId objectId) {
		return delegate.hasObject(objectId);
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId)
			throws MissingObjectException
		return delegate.open(objectId);
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		return delegate.open(objectId
	}

	@Override
	public RefUpdate updateRef(String ref) throws IOException {
		return delegate.updateRef(ref);
	}

	@Override
	public RefUpdate updateRef(String ref
		return delegate.updateRef(ref
	}

	@Override
	public RefRename renameRef(String fromRef
		return delegate.renameRef(fromRef
	}

	@Override
	public ObjectId resolve(String revstr) throws AmbiguousObjectException
			IOException {
		return delegate.resolve(revstr);
	}

	@Override
	public void incrementOpen() {
		throw new IllegalStateException();
	}

	@Override
	public void close() {
		throw new IllegalStateException();
	}

	@Override
	public String toString() {
		return "RepositoryDelegator+" + delegate.toString();
	}

	@Override
	public String getFullBranch() throws IOException {
		return delegate.getFullBranch();
	}

	@Override
	public String getBranch() throws IOException {
		return delegate.getBranch();
	}

	@Override
	public Set<ObjectId> getAdditionalHaves() {
		return delegate.getAdditionalHaves();
	}

	@Override
	public Ref getRef(String name) throws IOException {
		return delegate.getRef(name);
	}

	@Override
	public Map<String
		return delegate.getAllRefs();
	}

	@Override
	public Map<String
		return delegate.getTags();
	}

	@Override
	public Ref peel(Ref ref) {
		return delegate.peel(ref);
	}

	@Override
	public Map<AnyObjectId
		return delegate.getAllRefsByPeeledObjectId();
	}

	@Override
	public File getIndexFile() throws NoWorkTreeException {
		return delegate.getIndexFile();
	}

	@Override
	public DirCache readDirCache() throws NoWorkTreeException
			CorruptObjectException
		return delegate.readDirCache();
	}

	@Override
	public DirCache lockDirCache() throws NoWorkTreeException
			CorruptObjectException
		return delegate.lockDirCache();
	}

	@Override
	public RepositoryState getRepositoryState() {
		return delegate.getRepositoryState();
	}

	@Override
	public boolean isBare() {
		return delegate.isBare();
	}

	@Override
	public File getWorkTree() throws NoWorkTreeException {
		return delegate.getWorkTree();
	}

	@Override
	public void scanForRepoChanges() throws IOException {
		throw new IllegalStateException();
	}

	@Override
	public void notifyIndexChanged() {
		throw new IllegalStateException();
	}

	@Override
	public ReflogReader getReflogReader(String refName) throws IOException {
		return delegate.getReflogReader(refName);
	}

	@Override
	public String readMergeCommitMsg() throws IOException
		return delegate.readMergeCommitMsg();
	}

	@Override
	public void writeMergeCommitMsg(String msg) throws IOException {
		delegate.writeMergeCommitMsg(msg);
	}

	@Override
	public List<ObjectId> readMergeHeads() throws IOException
			NoWorkTreeException {
		return delegate.readMergeHeads();
	}

	@Override
	public void writeMergeHeads(List<ObjectId> heads) throws IOException {
		delegate.writeMergeHeads(heads);
	}

	public ObjectId readCherryPickHead() throws IOException
			NoWorkTreeException {
		return delegate.readCherryPickHead();
	}

	public void writeCherryPickHead(ObjectId head) throws IOException {
		delegate.writeCherryPickHead(head);
	}

	public void writeOrigHead(ObjectId head) throws IOException {
		delegate.writeOrigHead(head);
	}

	public ObjectId readOrigHead() throws IOException
		return delegate.readOrigHead();
	}

	public String readSquashCommitMsg() throws IOException {
		return delegate.readSquashCommitMsg();
	}

	public void writeSquashCommitMsg(String msg) throws IOException {
		delegate.writeSquashCommitMsg(msg);
	}

	public Map<AnyObjectId
		return delegate.getGrafts();
	}

	public Map<AnyObjectId
		return delegate.getReplacements();
	}
}
