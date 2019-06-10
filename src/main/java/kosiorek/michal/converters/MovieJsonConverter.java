package kosiorek.michal.converters;

import kosiorek.michal.model.Movie;

public class MovieJsonConverter extends JsonConverter<Movie> {

    public MovieJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }

}
