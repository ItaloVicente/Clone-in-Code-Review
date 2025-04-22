abstract class MultiKeyOperationImpl extends OperationImpl implements
    VBucketAware, KeyedOperation {
  protected final Map<String, Short> vbmap = new HashMap<String, Short>();

  protected MultiKeyOperationImpl(int c, int o, OperationCallback cb) {
    super(c, o, cb);
  }

  public Collection<String> getKeys() {
    return vbmap.keySet();
  }

  public Collection<MemcachedNode> getNotMyVbucketNodes() {
    return notMyVbucketNodes;
  }

  public void addNotMyVbucketNode(MemcachedNode node) {
    notMyVbucketNodes.add(node);
  }

  public void setNotMyVbucketNodes(Collection<MemcachedNode> nodes) {
    notMyVbucketNodes = nodes;
  }

  public void setVBucket(String k, short vb) {
    assert vbmap.containsKey(k) : "Key " + k + " not contained in operation";
    vbmap.put(k, new Short(vb));
  }

  public short getVBucket(String k) {
    assert vbmap.containsKey(k) : "Key " + k + " not contained in operation";
    return vbmap.get(k);
  }
