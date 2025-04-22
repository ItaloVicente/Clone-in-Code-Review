		movies.add(new MovieInfo("007: Quantum of Solace", "October 31, 2008", "Marc Forster", "Robert Wade"));
		movies.add(new MovieInfo("Batman Begins", "June 15, 2005", "Christopher Nolan", "David S. Goyer"));
		movies.add(new MovieInfo("Cloverfield", "January 18, 2008", "Matt Reeves", "Drew Goddard"));
		movies.add(new MovieInfo("The Dark Knight", "July 18, 2008", "Christopher Nolan", "David S. Goyer"));
		movies.add(new MovieInfo("Finding Nemo", "May 30, 2003", "Andrew Stanton", "Andrew Stanton"));
		movies.add(new MovieInfo("Get Smart", "June 20, 2008", "Peter Segal", "Tom J. Astle"));
		movies.add(new MovieInfo("Indiana Jones and the Kingdom of the Crystal Skull", "May 22, 2008",
				"Steven Spielberg", "Drunken Lemurs"));
		movies.add(new MovieInfo("Iron Man", "May 2, 2008", "Jon Favreau", "Mark Fergus"));
		movies.add(new MovieInfo("Raiders of the Lost Ark", "June 12, 1981", "Steven Spielberg", "George Lucas"));
		movies.add(new MovieInfo("Valkyrie", "December 25, 2008", "Bryan Singer", "Christopher McQuarrie"));
		movies.add(new MovieInfo("Wall-E", "June 27, 2008", "Andrew Stanton", "Andrew Stanton"));
		movies.add(new MovieInfo("Wanted", "June 27, 2008", "Timur Bekmambetov", "Michael Brandt"));

		ViewerSupport.bind(viewer, movies,
				BeanProperties.values(MovieInfo.class, new String[] { "title", "releaseDate", "director", "writer" }));
