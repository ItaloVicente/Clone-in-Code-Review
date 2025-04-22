      + VIEW_NAME_MAPRED + "\":{\"map\":\""
      + "function (doc) { if(doc.type == \\\"city\\\") {emit([doc.continent, "
      + "doc.country, doc.name], 1)}}\",\"reduce\":\"_sum\"},"
      + "\"" + VIEW_NAME_POPULATION_STRING + "\":{\"map\":\"function(doc, meta)"
      + "{\\n  if(doc.type == \\\"city\\\" && doc.population) {\\n    "
      + "emit(doc.population.toString(), null);\\n  }\\n}\"},"
      + "\"" + VIEW_NAME_POPULATION_INT + "\":{\"map\":\"function(doc, meta)"
      + "{\\n  if(doc.type == \\\"city\\\" && doc.population) "
      + "{\\n    emit(doc.population, null);\\n  }\\n}\"}}}";

