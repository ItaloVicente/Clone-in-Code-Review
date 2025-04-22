    private DiffAttributes attrs = null;
    private final String params;

    public JGitDiffAttributeViewImpl(final JGitPathImpl path,
                                     final String params) {
        super(path);
        this.params = params;
    }

    @Override
    public DiffAttributes readAttributes() throws IOException {
        if (attrs == null) {
            attrs = buildAttrs(path.getFileSystem(),
                               params);
        }
        return attrs;
    }

    @Override
    public Class<? extends BasicFileAttributeView>[] viewTypes() {
        return new Class[]{VersionAttributeView.class, VersionAttributeViewImpl.class, JGitDiffAttributeViewImpl.class};
    }

    private DiffAttributes buildAttrs(final JGitFileSystem fs,
                                      final String params) throws IOException {
        final String[] branches = params.split(",);
-        final String branchA = branches[0];
-        final String branchB = branches[1];
-        final List<FileDiff> diffs = fs.getGit().diffRefs(branchA, branchB);
-
-        return new DiffAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes(),
-                                      () -> diffs);
-    }
+	private DiffAttributes attrs = null;
+	private final String params;
+
+	public JGitDiffAttributeViewImpl(final JGitPathImpl path, final String params) {
+		super(path);
+		this.params = params;
+	}
+
+	@Override
+	public DiffAttributes readAttributes() throws IOException {
+		if (attrs == null) {
+			attrs = buildAttrs(path.getFileSystem(), params);
+		}
+		return attrs;
+	}
+
+	@Override
+	public Class<? extends BasicFileAttributeView>[] viewTypes() {
+		return new Class[] { VersionAttributeView.class, VersionAttributeViewImpl.class,
+				JGitDiffAttributeViewImpl.class };
+	}
+
+	private DiffAttributes buildAttrs(final JGitFileSystem fs, final String params) throws IOException {
+		final String[] branches = params.split(,);
+		final String branchA = branches[0];
+		final String branchB = branches[1];
+		final List<FileDiff> diffs = fs.getGit().diffRefs(branchA, branchB);
+
+		return new DiffAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes(), () -> diffs);
+	}
 }
diff --git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java
index 08efe33cfd..b41120456c 100644
--- a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java
@@ -20,56 +20,46 @@
 
 public class JGitEventsBroadcast {
 
-    public static final String DEFAULT_TOPIC = default-niogit-topic";
