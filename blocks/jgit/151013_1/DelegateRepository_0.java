
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;

public class DelegateRefDatabase extends RefDatabase {

  private Repository delegate;

  protected DelegateRefDatabase(Repository delegate) {
    this.delegate = delegate;
  }

  @Override
  public void create() throws IOException {
    delegate.getRefDatabase().create();
  }

  @Override
  public void close() {
    delegate.close();
  }

  @Override
  public boolean isNameConflicting(String name) throws IOException {
    return delegate.getRefDatabase().isNameConflicting(name);
  }

  @Override
  public RefUpdate newUpdate(String name
    return delegate.getRefDatabase().newUpdate(name
  }

  @Override
  public RefRename newRename(String fromName
    return delegate.getRefDatabase().newRename(fromName
  }

  @Override
  public Ref exactRef(String name) throws IOException {
    return delegate.getRefDatabase().exactRef(name);
  }

  @SuppressWarnings("deprecation")
  @Override
  public Map<String
    return delegate.getRefDatabase().getRefs(prefix);
  }

  @Override
  public List<Ref> getAdditionalRefs() throws IOException {
    return delegate.getRefDatabase().getAdditionalRefs();
  }

  @Override
  public Ref peel(Ref ref) throws IOException {
    return delegate.getRefDatabase().peel(ref);
  }

  protected Repository getDelegate() {
    return delegate;
  }
}
