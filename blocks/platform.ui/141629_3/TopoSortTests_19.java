		    switch (id) {
		    	case "A":
			    return Arrays.asList("C", "D");
		    	case "B":
			    return Collections.singleton("A");
		    	case "C":
			    return Collections.singleton("B");
		    	case "D":
			    return null;
		    	default:
			    break;
		    }
