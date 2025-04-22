
                state = State.READ_HEADER;
                return;
            } catch (Exception e) {
                out.add(invalidChunk(e));
                return;
            }
