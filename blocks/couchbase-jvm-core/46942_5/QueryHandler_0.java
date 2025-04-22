        long ttl = env().autoreleaseAfter();
        queryRowObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryErrorObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryStatusObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryInfoObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        querySignatureObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);

