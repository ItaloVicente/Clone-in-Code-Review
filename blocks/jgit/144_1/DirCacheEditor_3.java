
    private static final class PathEditComparator implements Comparator<PathEdit>{
        private final Charset pathEncoding;

        private PathEditComparator(Charset pathNameEncoding) {
            pathEncoding = pathNameEncoding;
        }

        public int compare(final PathEdit o1
            final byte[] a = Constants.encode(o1.path
            final byte[] b = Constants.encode(o2.path
            return DirCache.cmp(a
        }
    }
