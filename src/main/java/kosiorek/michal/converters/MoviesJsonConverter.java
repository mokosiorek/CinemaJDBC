package kosiorek.michal.converters;

import kosiorek.michal.model.MoviesJson;

public class MoviesJsonConverter extends JsonConverter<MoviesJson> {
    public MoviesJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
