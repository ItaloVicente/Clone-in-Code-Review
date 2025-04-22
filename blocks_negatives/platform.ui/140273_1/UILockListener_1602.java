                int newHead = newSize - (elements.length - head);
                System.arraycopy(elements, 0, newElements, 0, tail + 1);
                System.arraycopy(elements, head, newElements, newHead,
                        (newSize - newHead));
                head = newHead;
            }
            elements = newElements;
        }

        private int increment(int index) {
            return (index == (elements.length - 1)) ? 0 : index + 1;
        }

        /**
         * Remove the next semaphore to be woken up.
         * @return
         */
        public synchronized PendingSyncExec remove() {
            if (tail == head) {
