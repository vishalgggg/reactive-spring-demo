package ca.neilwhite.hrservice.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("books")
public class Book {
    
    
        @Id
        private Long id;
        private String name;
        
        @Builder.Default
        private List<Author> authors = new ArrayList<>();
    
       
    
        public static Mono<Book> fromRows(List<Map<String, Object>> rows) {
            return Mono.just(Book.builder()
                    .id((Long.parseLong(rows.get(0).get("b_id").toString())))
                    .name((String) rows.get(0).get("b_name"))
                    
                    .authors(rows.stream()
                            .map(Author::fromRow)
                            .filter(Objects::nonNull)
                            .toList())
                    .build());
        }
    }
    

