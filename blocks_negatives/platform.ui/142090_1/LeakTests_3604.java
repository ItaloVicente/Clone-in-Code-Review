            if (checkRef != null && checkRef.equals(ref)) {
                flag = true;
                break;
            }
        }

        assertTrue("Reference not enqueued", flag);
    }

    /**
     * @param queue
     * @param object
     * @return
     */
    private Reference createReference(ReferenceQueue queue, Object object) {
