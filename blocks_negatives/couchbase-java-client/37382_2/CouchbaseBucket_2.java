            public boolean process(byte value) throws Exception {
                counter++;
                if (value == open) {
                    depth++;
                }
                if (value == close) {
                    depth--;
                    if (depth == 0) {
                        markers.add(counter);
                    }
                }
                return true;
