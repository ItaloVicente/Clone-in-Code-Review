
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;

public class WrappedRefDirectory extends RefDirectory {

	private final RefDirectory wrapped;

	@Override
	public boolean hasVersioning() {
		return wrapped.hasVersioning();
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}

	@Override
	public Collection<String> getConflictingNames(String name)
			throws IOException {
		return wrapped.getConflictingNames(name);
	}

	@Override
	public boolean equals(Object obj) {
		return wrapped.equals(obj);
	}

	@Override
	public File logFor(String name) {
		return wrapped.logFor(name);
	}

	@Override
	public void create() throws IOException {
		wrapped.create();
	}

	@Override
	public void close() {
		wrapped.close();
	}

	@Override
	public void refresh() {
		wrapped.refresh();
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		return wrapped.isNameConflicting(name);
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		return wrapped.exactRef(name);
	}

	@Override
	public Map<String
		return wrapped.exactRef(refs);
	}

	@Override
	public Ref firstExactRef(String... refs) throws IOException {
		return wrapped.firstExactRef(refs);
	}

	@Override
	public String toString() {
		return wrapped.toString();
	}

	@Override
	public Map<String
		return wrapped.getRefs(prefix);
	}

	@Override
	public List<Ref> getRefs() throws IOException {
		return wrapped.getRefs();
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		return wrapped.getAdditionalRefs();
	}

	@Override
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		return wrapped.getRefsByPrefix(prefix
	}

	@Override
	public List<Ref> getRefsByPrefixWithExclusions(String include
			Set<String> excludes) throws IOException {
		return wrapped.getRefsByPrefixWithExclusions(include
	}

	@Override
	public List<Ref> getRefsByPrefix(String... prefixes) throws IOException {
		return wrapped.getRefsByPrefix(prefixes);
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		return wrapped.peel(ref);
	}

	@Override
	public Set<Ref> getTipsWithSha1(ObjectId id) throws IOException {
		return wrapped.getTipsWithSha1(id);
	}

	@Override
	public boolean hasFastTipsWithSha1() throws IOException {
		return wrapped.hasFastTipsWithSha1();
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		return wrapped.newUpdate(name
	}

	@Override
	public boolean hasRefs() throws IOException {
		return wrapped.hasRefs();
	}

	@Override
	public RefDirectoryRename newRename(String fromName
			throws IOException {
		return wrapped.newRename(fromName
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate() {
		return wrapped.newBatchUpdate();
	}

	@Override
	public boolean performsAtomicTransactions() {
		return wrapped.performsAtomicTransactions();
	}

	@Override
	public void pack(List<String> refs) throws IOException {
		wrapped.pack(refs);
	}

	WrappedRefDirectory(RefDirectory wrapped) {
		super((FileRepository) wrapped.getRepository());

		this.wrapped = wrapped;
	}

}
