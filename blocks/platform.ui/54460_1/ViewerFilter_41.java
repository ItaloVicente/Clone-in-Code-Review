public abstract class ViewerFilter<E,I> {
    protected ViewerFilter() {
    }

    public E[] filter(Viewer<I> viewer, Object parent, E[] elements) {
        int size = elements.length;
        ArrayList<E> out = new ArrayList<E>(size);
        E element = null;
        for (int i = 0; i < size; ++i) {
            element = elements[i];
            if (select(viewer, parent, element)) {
