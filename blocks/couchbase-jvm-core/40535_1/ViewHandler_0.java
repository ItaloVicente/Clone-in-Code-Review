        if (closingPointer > 0) {
            info.setByte(closingPointer, '}');
            viewInfoObservable.onNext(info);
        } else {
            viewInfoObservable.onNext(Unpooled.EMPTY_BUFFER);
        }
