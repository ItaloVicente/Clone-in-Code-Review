            PendingSyncExec result = elements[head];
            elements[head] = null;
            head = increment(head);
            if (tail == head && elements.length > BASE_SIZE) {
                elements = new PendingSyncExec[BASE_SIZE];
                tail = head = 0;
            }
            return result;
        }
