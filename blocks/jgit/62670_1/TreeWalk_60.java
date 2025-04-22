import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
import org.eclipse.jgit.attributes.AttributesProvider;
import org.eclipse.jgit.dircache.DirCacheIterator;
