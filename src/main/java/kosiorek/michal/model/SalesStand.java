package kosiorek.michal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesStand {

    private int id;
    private int customerId;
    private int movieId;
    private LocalDateTime startDateTime;

}
