package omid.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldsMessage implements Serializable {
    private final long serialVersionUID = 8867390621007085624L;
    private UUID id;
    private String message;
}
