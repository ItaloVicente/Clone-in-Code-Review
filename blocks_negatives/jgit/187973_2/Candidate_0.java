	 * Consider the history "A<-B<-C". If the result file S in C was renamed to
	 * R in B, the rename score for this rename will be held in this field by
	 * the candidate object for B. By storing the score with B, the application
	 * can see what the rename score was as it makes the transition from C/S to
	 * B/R. This may seem backwards since it was C that performed the rename,
	 * but the application doesn't learn about path R until B.
