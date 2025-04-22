    /**
     * The Queue is the construct that keeps track of Semaphores.
     */
    public static class Queue {
        private static final int BASE_SIZE = 8;

        protected PendingSyncExec[] elements = new PendingSyncExec[BASE_SIZE];

        protected int head = 0;

        protected int tail = 0;

        /**
         * Add the semaphore to the queue.
         * @param element
         */
        public synchronized void add(PendingSyncExec element) {
            int newTail = increment(tail);
            if (newTail == head) {
                grow();
                newTail = tail + 1;
            }
            elements[tail] = element;
            tail = newTail;
        }

        private void grow() {
            int newSize = elements.length * 2;
            PendingSyncExec[] newElements = new PendingSyncExec[newSize];
            if (tail >= head) {
