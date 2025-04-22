            byte docFlags = 0;
            if (mut.createDocument()) {
                docFlags |= SUBDOC_FLAG_MKDOC;
            }

            if (docFlags != 0) {
                extrasLength += 1;
                extras.writeByte(docFlags);
            }

