            if (mode == Mode.JSON_NUMBER_VALUE && content.readableBytes() > 2) {
                length = 1;
                level.setCurrentValue(this.content.copy(readerIndex - 1, length), length);
                this.content.discardReadBytes();
                level.emitJsonPointerValue();
            } else {
                throw NEED_MORE_DATA;
            }
