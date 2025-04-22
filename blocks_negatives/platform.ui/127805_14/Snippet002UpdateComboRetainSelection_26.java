    private static IObservableFactory getListDetailFactory() {
        return new IObservableFactory() {
            @Override
			public IObservable createObservable(Object target) {
                WritableList list = WritableList.withElementType(String.class);
                list.addAll((Collection) target);
                return list;
            }
        };
    }
