    public static int hashCode(boolean b) {
        return b ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }

    public static int hashCode(int i) {
        return i;
    }

    public static int hashCode(Object object) {
        return object != null ? object.hashCode() : 0;
    }

    public static Collection safeCopy(Collection collection, Class c) {
        return safeCopy(collection, c, false);
    }
