        final List<DCPRequest> items = new ArrayList<DCPRequest>();
        addStream.stream().subscribe(new Action1<DCPRequest>() {
            @Override
            public void call(DCPRequest dcpRequest) {
                items.add(dcpRequest);
            }
        });
